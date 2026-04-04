package com.servicios.sppp.back_end_sppp.servicios;

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

    public String subirArchivo(MultipartFile archivo, String carpeta) throws Exception {
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
        
        return minioUrl + "/" + bucketName + "/" + rutaCompleta;
    }

    public void eliminarArchivo(String url) throws Exception {
        String ruta = url.replace(minioUrl + "/" + bucketName + "/", "");
        minioClient.removeObject(
            RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(ruta)
                .build()
        );
    }
}