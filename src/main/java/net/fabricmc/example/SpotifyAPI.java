package net.fabricmc.example;

import com.google.gson.Gson;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import com.wrapper.spotify.requests.data.player.PauseUsersPlaybackRequest;
import com.wrapper.spotify.requests.data.player.StartResumeUsersPlaybackRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class SpotifyAPI {
    public static final SpotifyAPI INSTANCE = new SpotifyAPI();
    private SpotifyApi API;
    private Gson gson;

    private static final String CLIENT_ID = "";
    private static final String CLIENT_SECRET = "";
    private static URI REDIRECT_URI = null;
    private String code = "";

    public SpotifyAPI() {
        try {
            connect();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect() throws IOException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    REDIRECT_URI = SpotifyHttpManager.makeUri("https://example.com/spotify-redirect");//"http://localhost:4444/");
                    API = new SpotifyApi.Builder()
                            .setClientId(CLIENT_ID)
                            .setClientSecret(CLIENT_SECRET)
                            .setRedirectUri(REDIRECT_URI)
                            .build();

                    AuthorizationCodeUriRequest authorizationCodeUriRequest = API.authorizationCodeUri()
                            .scope("user-modify-playback-state")
                            .build();

                    final URI uriResult = authorizationCodeUriRequest.execute();
                    System.out.println(uriResult);

                    /*ServerSocket server = new ServerSocket(4444);
                    Socket client = server.accept();
                    PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    out.write("<!DOCTYPE html><html>whoopty</html>");
                    String line = in.readLine();
                    int startIndex = line.indexOf('=') + 1;
                    int lastIndex = line.lastIndexOf(' ');*/
                    code = "";//line.substring(startIndex, lastIndex);

                    AuthorizationCodeRequest authorizationCodeRequest = API.authorizationCode(code).build();
                    AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();
                    API.setAccessToken(authorizationCodeCredentials.getAccessToken());
                    API.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
                    System.out.println("Expires in " + authorizationCodeCredentials.getExpiresIn());

                    pause();
                    play();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (SpotifyWebApiException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    public void skip() {
        API.skipUsersPlaybackToNextTrack();
    }

    public void pause() throws ParseException, SpotifyWebApiException, IOException {
        PauseUsersPlaybackRequest request = API.pauseUsersPlayback().build();
        request.execute();
    }

    public void play() throws ParseException, SpotifyWebApiException, IOException {
        StartResumeUsersPlaybackRequest request = API.startResumeUsersPlayback().build();
        request.execute();
    }

    public void previous() {
        API.skipUsersPlaybackToPreviousTrack();
    }
}
