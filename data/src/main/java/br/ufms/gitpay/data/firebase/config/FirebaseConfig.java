package br.ufms.gitpay.data.firebase.config;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class FirebaseConfig {

    private static final String SERVICE_ACCOUNT_PATH = "serviceAccountKey.json";
    private static final Firestore FIRESTORE = initializeFirestore(false);

    private static Firestore initializeFirestore() {
        return initializeFirestore(true);
    }

    private static Firestore initializeFirestore(boolean preWarmConnection) {
        try (InputStream serviceAccount = Files.newInputStream(
                Paths.get(Objects.requireNonNull(
                        FirebaseConfig.class.getClassLoader().getResource(SERVICE_ACCOUNT_PATH),
                        "Arquivo " + SERVICE_ACCOUNT_PATH + " não encontrado"
                ).toURI()))) {

            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();

            FirebaseApp.initializeApp(options);
            if (preWarmConnection) {
                preWarmConnection();
            }
            return FirestoreClient.getFirestore();

        } catch (IOException | URISyntaxException | ExecutionException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void preWarmConnection() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> querySnapshot = FirestoreClient.getFirestore().collection("preWarm").limit(1).get();
        querySnapshot.get();
    }

    public static Firestore getFirestore() {
        return FIRESTORE;
    }
}
