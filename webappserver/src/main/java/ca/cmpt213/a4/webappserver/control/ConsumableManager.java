package ca.cmpt213.a4.webappserver.control;

import ca.cmpt213.a4.webappserver.model.Consumable;
import ca.cmpt213.a4.webappserver.model.ConsumableFactory;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class ConsumableManager {

    private static final ArrayList<Consumable> consumableList = new ArrayList<>();
    private static final ConsumableFactory factory = new ConsumableFactory();

    /**
     * Method that returns the arraylist of all items
     * @return - the arraylist of all items
     */
    public static ArrayList<Consumable> allItems() {
        return consumableList;
    }

    /**
     * Method that adds an item to the server-side arraylist
     * @param consumable - the item to be added
     */
    public static void addItem(Consumable consumable) {
        consumableList.add(consumable);
        Collections.sort(consumableList);
    }

    /**
     * Method that removes an item from the server-side arraylist
     * @param consumable - the item to be removed
     */
    public static void removeItem(Consumable consumable) {
        for (Consumable i : consumableList) {
            if ( i.toString().equals( consumable.toString() ) ) {
                consumableList.remove(i);
                return;
            }
        }
    }

    /**
     * Method that returns an arraylist of all expired items
     * @return - an arraylist of all expired items
     */
    public static ArrayList<Consumable> expiredItems() {
        ArrayList<Consumable> expiredItems = new ArrayList<>();
        for (Consumable i : consumableList) {
            if (i.isExpired()) {
                expiredItems.add(i);
            }
        }
        return expiredItems;
    }

    /**
     * Method that returns an arraylist of all non-expired items
     * @return - an arraylist of all non-expired items
     */
    public static ArrayList<Consumable> nonExpiredItems() {
        ArrayList<Consumable> nonExpiredItems = new ArrayList<>();
        for (Consumable i : consumableList) {
            if (!i.isExpired()) {
                nonExpiredItems.add(i);
            }
        }
        return nonExpiredItems;
    }

    /**
     * Method that returns an arraylist of all items expiring in 7 days
     * @return - an arraylist of all items expiring in 7 days
     */
    public static ArrayList<Consumable> inSevenDays() {
        ArrayList<Consumable> inSevenDays = new ArrayList<>();
        for (Consumable i : consumableList) {
            if (i.inSevenDays()) {
                inSevenDays.add(i);
            }
        }
        return inSevenDays;
    }

    /**
     * Method that saves the consumable arraylist into a json file
     */
    public static void saveConsumables() {
        Gson myGson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
                new TypeAdapter<LocalDateTime>() {
                    @Override
                    public void write(JsonWriter jsonWriter,
                                      LocalDateTime localDateTime) throws IOException {
                        jsonWriter.value(localDateTime.toString());
                    }
                    @Override
                    public LocalDateTime read(JsonReader jsonReader) throws IOException {
                        return LocalDateTime.parse(jsonReader.nextString());
                    }
                }).create();

        try {
            Writer writer = new FileWriter("./consumableList.json");
            myGson.toJson(consumableList, writer);
            writer.flush();
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that loads the contents of the json file into the arraylist
     */
    public static void loadConsumables() {
        File file = new File("./consumableList.json");
        try {
            JsonElement fileElement = JsonParser.parseReader(new FileReader(file));

            JsonArray jsonArray = fileElement.getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject taskObj = jsonArray.get(i).getAsJsonObject();
                String taskDate = taskObj.get("expiryDate").getAsString();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                LocalDateTime localDateTime = LocalDateTime.parse(taskDate, formatter);
                consumableList.add(factory.getInstance(
                        taskObj.get("type").getAsInt(),
                        taskObj.get("name").getAsString(),
                        taskObj.get("notes").getAsString(),
                        taskObj.get("price").getAsDouble(),
                        taskObj.get("size").getAsDouble(),
                        localDateTime
                ));
            }

        }
        catch (FileNotFoundException e) {
        }
    }



}
