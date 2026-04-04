package com.servicios.sppp.back_end_sppp.servicios;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class ArchivoServicio {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Value("${minio.url}")
    private String minioUrl;

    public ResultadoArchivo subirArchivo(MultipartFile archivo, String carpeta) throws Exception {
        String nombreOriginal = archivo.getOriginalFilename();
        String extension = nombreOriginal != null && nombreOriginal.contains(".") 
            ? nombreOriginal.substring(nombreOriginal.lastIndexOf(".")) 
            : "";
        String nombreArchivo = UUID.randomUUID().toString() + extension;
        
        String rutaCompleta = carpeta + "/" + nombreArchivo;
        
        InputStream inputStream = archivo.getInputStream();
        String contentType = archivo.getContentType();
        
        boolean existeBucket = minioClient.bucketExists(io.minio.BucketExistsArgs.builder().bucket(bucketName).build());
        if (!existeBucket) {
            minioClient.makeBucket(io.minio.MakeBucketArgs.builder().bucket(bucketName).build());
        }
        
        minioClient.putObject(
            PutObjectArgs.builder()
                .bucket(bucketName)
                .object(rutaCompleta)
                .stream(inputStream, archivo.getSize(), -1)
                .contentType(contentType)
                .build()
        );
        
        String urlPrefirmada = generarUrlPrefirmada(rutaCompleta);
        ResultadoArchivo resultado = new ResultadoArchivo();
        resultado.setRuta(rutaCompleta);
        resultado.setUrl(minioUrl + "/" + rutaCompleta);
        resultado.setUrlPrefirmada(urlPrefirmada);
        
        return resultado;
    }

    public String generarUrlPrefirmada(String rutaArchivo) throws Exception {
        try {
            String url = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName)
                    .object(rutaArchivo)
                    .method(io.minio.http.Method.GET)
                    .expiry(7 * 24 * 60 * 60)
                    .build()
            );
            return url;
        } catch (Exception e) {
            return minioUrl + "/" + bucketName + "/" + rutaArchivo;
        }
    }

    public void eliminarArchivo(String ruta) throws Exception {
        minioClient.removeObject(
            RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(ruta)
                .build()
        );
    }

    public static class ResultadoArchivo {
        private String ruta;
        private String urlPrefirmada;
        private String url;

        public String getRuta() {
            return ruta;
        }

        public void setRuta(String ruta) {
            this.ruta = ruta;
        }

        public String getUrlPrefirmada() {
            return urlPrefirmada;
        }

        public void setUrlPrefirmada(String urlPrefirmada) {
            this.urlPrefirmada = urlPrefirmada;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}