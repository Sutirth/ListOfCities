package util;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class JsonFileReader {

    public static String parseJsonFileFromAssets(Context context, String fileName){
        String json = null;
        try{
            InputStream inputStreamParser = context.getAssets().open(fileName);
            int size = inputStreamParser.available();
            byte[] bufferPool = new byte[size];
            inputStreamParser.read(bufferPool);
            inputStreamParser.close();
            json = new String(bufferPool, "UTF-8");

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return json;
    }
}
