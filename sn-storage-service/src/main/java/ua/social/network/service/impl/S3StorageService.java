package ua.social.network.service.impl;

import java.util.UUID;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import ua.social.network.domain.FileMetadata;
import ua.social.network.service.StorageService;

/**
 * @author Mykola Yashchenko
 */
@Service
@Profile("production")
public class S3StorageService implements StorageService {

    private final AmazonS3 amazonS3;
    private final String bucketName;

    public S3StorageService(@Value("${aws.credentials.access_key}") final String accessKey,
                            @Value("${aws.credentials.secret_key}") final String secretKey,
                            @Value("${aws.s3.bucket_name}") final String bucketName) {
        final BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
        this.bucketName = bucketName;
    }

    @Override
    public String store(final FileMetadata fileMetadata) {
        final ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(fileMetadata.contentLength);
        metadata.setContentType(fileMetadata.contentType);

        final String newFileName = UUID.randomUUID().toString() + "_" + fileMetadata.fileName;

        final PutObjectRequest request = new PutObjectRequest(
                bucketName, newFileName, fileMetadata.inputStream, metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead);

        amazonS3.putObject(request);

        return amazonS3.getUrl(bucketName, newFileName).toString();
    }
}
