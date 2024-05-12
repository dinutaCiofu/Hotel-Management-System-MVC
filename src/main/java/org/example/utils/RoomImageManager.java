package org.example.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomImageManager {
    private static final Map<Integer, List<String>> roomImages = new HashMap<>();

    public static void addImages(Integer roomId, List<String> images) {
        roomImages.put(roomId, images);
    }

    public static List<String> getImages(Integer roomId) {
        return roomImages.getOrDefault(roomId, null);
    }
}
