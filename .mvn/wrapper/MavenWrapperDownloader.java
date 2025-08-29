/*
 * Copyright 2007-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Properties;

public class MavenWrapperDownloader {

    private static final String WRAPPER_VERSION = "3.2.0";
    private static final String DEFAULT_DOWNLOAD_URL = "https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/" + WRAPPER_VERSION + "/maven-wrapper-" + WRAPPER_VERSION + ".jar";

    public static void main(String args[]) {
        try {
            System.out.println("- Downloader started");
            File wrapperJar = wrapperJar();
            if (args.length > 0) {
                System.out.println("- Downloading from: " + args[0]);
                downloadFileFromURL(args[0], wrapperJar);
                System.out.println("Done");
            } else {
                System.out.println("- Downloading from: " + DEFAULT_DOWNLOAD_URL);
                downloadFileFromURL(DEFAULT_DOWNLOAD_URL, wrapperJar);
                System.out.println("Done");
            }
        } catch (Exception e) {
            System.out.println("- Error downloading");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static File wrapperJar() {
        return new File(".mvn/wrapper/maven-wrapper.jar");
    }

    private static void downloadFileFromURL(String urlString, File destination) throws Exception {
        if (System.getenv("MVNW_USERNAME") != null && System.getenv("MVNW_PASSWORD") != null) {
            System.out.println("- Downloading with authentication");
            downloadFileFromURLWithAuthentication(urlString, destination);
        } else {
            downloadFileFromURLWithoutAuthentication(urlString, destination);
        }
    }

    private static void downloadFileFromURLWithoutAuthentication(String urlString, File destination) throws Exception {
        URL website = new URL(urlString);
        try (ReadableByteChannel rbc = Channels.newChannel(website.openStream());
             FileOutputStream fos = new FileOutputStream(destination)) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
    }

    private static void downloadFileFromURLWithAuthentication(String urlString, File destination) throws Exception {
        URL website = new URL(urlString);
        java.net.URLConnection conn = website.openConnection();
        String username = System.getenv("MVNW_USERNAME");
        String password = System.getenv("MVNW_PASSWORD");
        String userpass = username + ":" + password;
        String basicAuth = "Basic " + java.util.Base64.getEncoder().encodeToString(userpass.getBytes());
        conn.setRequestProperty("Authorization", basicAuth);
        try (ReadableByteChannel rbc = Channels.newChannel(conn.getInputStream());
             FileOutputStream fos = new FileOutputStream(destination)) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
    }
}
