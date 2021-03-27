package net.fabricmc.example;

import com.google.gson.Gson;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import com.wrapper.spotify.requests.data.player.*;
import org.apache.hc.core5.http.ParseException;

import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SpotifyAPI {
    public static final SpotifyAPI INSTANCE = new SpotifyAPI();
    private SpotifyApi API;
    private Gson gson;

    private static final String CLIENT_ID = "no id for u";
    private static final String CLIENT_SECRET = "no secret for u";
    private static URI REDIRECT_URI = null;
    private String code = "";

    private BlockingQueue<Integer> volumeQueue = new ArrayBlockingQueue<Integer>(1024);
    private final Object lock = new Object();
    private boolean play = false;
    private boolean pause = false;
    private boolean forward = false;
    private boolean backward = false;
    private int currentVolume = 500;

    public SpotifyAPI() {
        try {
            startThread();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startThread() throws IOException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                REDIRECT_URI = SpotifyHttpManager.makeUri("https://example.com/spotify-redirect");//"http://localhost:4444/");
                API = new SpotifyApi.Builder()
                        .setClientId(CLIENT_ID)
                        .setClientSecret(CLIENT_SECRET)
                        .setRedirectUri(REDIRECT_URI)
                        .build();

                /*AuthorizationCodeUriRequest authorizationCodeUriRequest = API.authorizationCodeUri()
                        .scope("user-modify-playback-state")
                        .build();

                final URI uriResult = authorizationCodeUriRequest.execute();
                System.out.println(uriResult);*/

                    /*ServerSocket server = new ServerSocket(4444);
                    Socket client = server.accept();
                    PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    out.write("<!DOCTYPE html><html>whoopty</html>");
                    String line = in.readLine();
                    int startIndex = line.indexOf('=') + 1;
                    int lastIndex = line.lastIndexOf(' ');*/
                code = "";//line.substring(startIndex, lastIndex);

                /*AuthorizationCodeRequest authorizationCodeRequest = API.authorizationCode(code).build();
                AuthorizationCodeCredentials authorizationCodeCredentials = null;
                try {
                    authorizationCodeCredentials = authorizationCodeRequest.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                } catch (SpotifyWebApiException e) {
                    e.printStackTrace();
                    return;
                } catch (ParseException e) {
                    e.printStackTrace();
                    return;
                }*/
                API.setAccessToken("no token for u");
                API.setRefreshToken("no token for u");
                //API.setAccessToken(authorizationCodeCredentials.getAccessToken());
                //API.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
                //System.out.println("Expires in " + authorizationCodeCredentials.getExpiresIn());

                while (true) {

                    try {
                        //synchronized (lock) {
                            if (forward) {
                                SkipUsersPlaybackToNextTrackRequest request = API.skipUsersPlaybackToNextTrack().build();
                                request.execute();
                                forward = false;
                            }
                            if (backward) {
                                SkipUsersPlaybackToPreviousTrackRequest request = API.skipUsersPlaybackToPreviousTrack().build();
                                request.execute();
                                backward = false;
                            }
                            if (pause) {
                                PauseUsersPlaybackRequest request = API.pauseUsersPlayback().build();
                                request.execute();
                                pause = false;
                            }
                            if (play) {
                                StartResumeUsersPlaybackRequest request = API.startResumeUsersPlayback().build();
                                request.execute();
                                play = false;
                            }
                        //}
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SpotifyWebApiException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (volumeQueue.isEmpty())
                        continue;

                    Integer volume = null;
                    try {
                        volume = volumeQueue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        continue;
                    }

                    if (volume == null)
                        continue;

                    try {
                        SetVolumeForUsersPlaybackRequest request = API.setVolumeForUsersPlayback(volume).build();
                        request.execute();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch (SpotifyWebApiException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        thread.start();
    }

    public void setVolume(int percent) {
        try {
            volumeQueue.put(percent);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void skip() {
        //synchronized (lock) {
            forward = true;
        //}
    }

    public void pause() {
        //synchronized (lock) {
            pause = true;
        //}
    }

    public void play() {
        //synchronized (lock) {
            play = true;
        //}
    }

    public void previous() {
        //synchronized (lock) {
            backward = true;
        //}
    }
}
