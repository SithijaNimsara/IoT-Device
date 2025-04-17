package org.iot.device;

import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.iot.device.entity.IoTDevice;
import org.iot.device.entity.ResponseEntity;
import org.iot.device.repository.IoTDeviceRepository;
import org.iot.device.service.IoTDeviceRepositoryService;
import org.iot.device.util.JsonUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        // used ConcurrentHashMap for the thread safety
        final Map<Integer, IoTDevice> deviceMap = new ConcurrentHashMap<>();
        IoTDeviceRepository ioTDeviceRepository = new IoTDeviceRepository(deviceMap);
        IoTDeviceRepositoryService ioTDeviceRepositoryService = new IoTDeviceRepositoryService(ioTDeviceRepository);

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/iot", new ApiConfig(ioTDeviceRepositoryService));
        server.setExecutor(null);
        server.start();
    }

    public static class ApiConfig implements HttpHandler {

        IoTDeviceRepositoryService ioTDeviceRepositoryService;

        public ApiConfig(IoTDeviceRepositoryService ioTDeviceService) {
            this.ioTDeviceRepositoryService = ioTDeviceService;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            String response = "";
            int code = 0;
            System.out.println(method);
            try {
                if ("GET".equals(method)) {
                    if(path.matches("/iot?$")) {
                        System.out.println("IF");
                        ResponseEntity<List<IoTDevice>> ioTDeviceAll = ioTDeviceRepositoryService.findAll();
                        code = ioTDeviceAll.getStatus().getCode();
                        response = JsonUtil.toJson(ioTDeviceAll);
                    } else if (path.matches("/iot/\\d+")) {
                        System.out.println("ELSE IF");
                        int id = Integer.parseInt(path.substring(5));
                        ResponseEntity<IoTDevice> ioTDeviceById = ioTDeviceRepositoryService.findById(id);
                        code = ioTDeviceById.getStatus().getCode();
                        response = JsonUtil.toJson(ioTDeviceById);
                    } else {
                        code = 400;
                        response = "Bad Request";
                    }
                } else if ("POST".equals(method)) {
                    IoTDevice ioTDevice = JsonUtil.fromJson(exchange.getRequestBody(), IoTDevice.class);
                    ResponseEntity<String> ioTDeviceService = ioTDeviceRepositoryService.save(ioTDevice);
                    code = ioTDeviceService.getStatus().getCode();
                    response = ioTDeviceService.getData().toString();
                    System.out.println(ioTDeviceService.toString());
                } else if ("PUT".equals(method)) {
                    int id = Integer.parseInt(path.substring(5));
                    IoTDevice ioTDevice = JsonUtil.fromJson(exchange.getRequestBody(), IoTDevice.class);
                    ResponseEntity<String> ioTDeviceService = ioTDeviceRepositoryService.update(id, ioTDevice);
                    code = ioTDeviceService.getStatus().getCode();
                    response = ioTDeviceService.getData().toString();
                } else if ("DELETE".equals(method)) {
                    int id = Integer.parseInt(path.substring(5));
                    ResponseEntity<IoTDevice> ioTDeviceService = ioTDeviceRepositoryService.delete(id);
                    code = ioTDeviceService.getStatus().getCode();
                    response = ioTDeviceService.getData().toString();
                } else {
                    code = 405;
                    response = "Method Not Allowed";
                }
            } catch (JsonSyntaxException | NumberFormatException e) {
                code = 400;
                response = "Invalid request: " + e.getMessage();
            } catch (Exception e) {
                code = 500;
                response = "Internal Server Error: " + e.getMessage();
            }
            exchange.sendResponseHeaders(code, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}