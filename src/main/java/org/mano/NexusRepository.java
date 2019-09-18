package org.mano;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.ws.rs.core.HttpHeaders;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class NexusRepository implements RepoTargetFactory {

    String DIRECTORY_KEY= "raw.directory";
    String ASSET_KEY= "raw.asset1";
    String FILENAME_KEY= "raw.asset1.filename";

    String repoUrl;
    String userName;
    String password;

    @Override
    public void setRepoConfigurations(String repoUrl, String userName, String password) {
        this.repoUrl = repoUrl;
        this.userName = userName;
        this.password = password;
    }

    public String pushToRepository() {
        HttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost postRequest = new HttpPost(repoUrl) ;
        String auth = userName + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(StandardCharsets.ISO_8859_1));
        String authHeader = "Basic " + new String(encodedAuth);
        postRequest.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
        try
        {
            byte[] packageBytes = "Hello. This is my file content".getBytes();
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            InputStream packageStream = new ByteArrayInputStream(packageBytes);
            InputStreamBody inputStreamBody = new InputStreamBody(packageStream, ContentType.APPLICATION_OCTET_STREAM);
            multipartEntityBuilder.addPart(DIRECTORY_KEY, new StringBody("DIRECTORY"));
            multipartEntityBuilder.addPart(FILENAME_KEY, new StringBody("MyFile.txt"));
            multipartEntityBuilder.addPart(ASSET_KEY, inputStreamBody);
            HttpEntity entity = multipartEntityBuilder.build();
            postRequest.setEntity(entity); ;

            HttpResponse response = httpclient.execute(postRequest) ;
            if (response != null)
            {
                System.out.println(response.getStatusLine().getStatusCode());
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace() ;
        }
        return null;
    }

}
