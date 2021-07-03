package at.dogan.simpleDeathTimeout.uuid;

import java.io.IOException;
import java.util.UUID;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class PlayerUUID {
    /**
     * Get the UUID from an playername
     *
     * @param name Name of the Player
     * @return the uuid
     */
    public static UUID getUUID(String name) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("https://api.mojang.com/users/profiles/minecraft/" + name)
                    .method("GET", null).build();
            Response response = client.newCall(request).execute();

            if (!response.isSuccessful())
                return null;

            ResponseJson json = new Gson().fromJson(response.body().string(), ResponseJson.class);

            if (json == null)
                return null;

            String uuid = json.id;
            uuid = insertChar('-', 8, uuid);
            uuid = insertChar('-', 13, uuid);
            uuid = insertChar('-', 18, uuid);
            uuid = insertChar('-', 23, uuid);

            return UUID.fromString(uuid);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String insertChar(char c, int pos, String string) {
        StringBuilder builder = new StringBuilder();

        builder.append(string.substring(0, pos));
        builder.append(c);
        builder.append(string.substring(pos, string.length()));

        return builder.toString();
    }
}
