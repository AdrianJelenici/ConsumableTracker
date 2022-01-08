package ca.cmpt213.a4.client.control;

import ca.cmpt213.a4.client.model.Consumable;
import ca.cmpt213.a4.client.model.ConsumableFactory;
import ca.cmpt213.a4.client.view.AddGUI;
import ca.cmpt213.a4.client.view.RemoveGUI;
import com.google.gson.*;
import org.json.JSONObject;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for managing consumable items in an arraylist
 */
public class ConsumableManager {

    // TODO: CHANGE CONSUMABLELIST BACK TO PRIVATE

    public static final ArrayList<Consumable> consumableList = new ArrayList<>();
    private static final ConsumableFactory factory = new ConsumableFactory();

    /**
     * Method that displays all the items in the arraylist
     * @return - a JPanel that will then be passed onto a JScroll Pane in the mainGUI
     */
    public JPanel allItems() {

        JPanel mainPanel = new JPanel();
        Box box = Box.createVerticalBox();
        String type;

        if (consumableList.isEmpty()) {
            JPanel noItems = new JPanel();
            JLabel text = new JLabel("No Items To Show");
            text.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            text.setHorizontalAlignment(JLabel.CENTER);
            noItems.setLayout(new GridLayout(1, 1));
            noItems.add(text);
            return noItems;
        } else {
            int counter = 1;
            for(Consumable i : consumableList){
                if (i.getType() == 1) {
                    type = "(Food)";
                } else {
                    type = "(Drink)";
                }
                JLabel item = new JLabel("<html> <br> Item #"
                        + counter + " " + type + "<html>");
                item.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
                JLabel text = new JLabel(i.toString());
                text.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
                JLabel line1 = new JLabel("------------------------------------------------------");
                line1.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
                box.add(item);
                box.add(text);
                box.add(line1);
                counter++;
            }
        }
        mainPanel.add(box);
        return mainPanel;

    }

    /**
     * Method that adds a consumable item to the server-side arraylist
     * It then updates the local arraylist
     * @param parent - the frame which the dialog window will appear on
     */
    public void addItem(JFrame parent) throws IOException {

        AddGUI dialog = new AddGUI(parent);
        Consumable item = dialog.showAddDialog();

        if (item != null) {
            String consumable = new JSONObject()
                    .put("type", item.getType())
                    .put("name", item.getName())
                    .put("notes", item.getNotes())
                    .put("price", item.getPrice())
                    .put("size", item.getSize())
                    .put("expiryDate", item.getExpiryDate())
                    .toString();

            if (item.getType() == 1) {

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/addFood"))
                        .header("content-type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(consumable))
                        .build();
                try {
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    System.out.println(response);
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                }

            } else {

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/addDrink"))
                        .header("content-type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(consumable))
                        .build();
                try {
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    System.out.println(response);
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                }

            }

            loadItems();
        }

    }

    /**
     * Method that removes a consumable item from the server-side arraylist
     * It then updates the local arraylist
     * @param parent - the frame which the dialog window will appear on
     */
    public void removeItem(JFrame parent) throws IOException {

        RemoveGUI dialog = new RemoveGUI(parent,consumableList.size());
        int number = dialog.showRemoveDialog();

        if (!(number == 0 || number > consumableList.size())) {

            Consumable item = consumableList.get(number - 1);
            String consumable = new JSONObject()
                    .put("type", item.getType())
                    .put("name", item.getName())
                    .put("notes", item.getNotes())
                    .put("price", item.getPrice())
                    .put("size", item.getSize())
                    .put("expiryDate", item.getExpiryDate())
                    .toString();

            if (item.getType() == 1) {

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/removeFood"))
                        .header("content-type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(consumable))
                        .build();
                try {
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    System.out.println(response);
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                }

            } else {

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/removeDrink"))
                        .header("content-type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(consumable))
                        .build();
                try {
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    System.out.println(response);
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                }

            }

            loadItems();

        }

    }

    /**
     * Method that displays all the expired items in the arraylist
     * @return - a JPanel that will then be passed onto a JScroll Pane in the mainGUI
     */
    public JPanel expiredItems() {

        List<Consumable> list = new ArrayList<>();
        try {
            loadCertainItems(list, "http://localhost:8080/listExpired");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        JPanel mainPanel = new JPanel();
        Box box = Box.createVerticalBox();
        String type;

        if (list.isEmpty()) {
            JPanel noItems = new JPanel();
            JLabel text = new JLabel("No Items To Show");
            text.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            text.setHorizontalAlignment(JLabel.CENTER);
            noItems.setLayout(new GridLayout(1, 1));
            noItems.add(text);
            return noItems;
        } else {
            int counter = 1;
            for(Consumable i : list){
                if (i.getType() == 1) {
                    type = "(Food)";
                } else {
                    type = "(Drink)";
                }
                JLabel item = new JLabel("<html> <br> Item #"
                        + counter + " " + type + "<html>");
                item.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
                JLabel text = new JLabel(i.toString());
                text.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
                JLabel line1 = new JLabel("------------------------------------------------------");
                line1.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
                box.add(item);
                box.add(text);
                box.add(line1);
                counter++;
            }
        }
        mainPanel.add(box);
        return mainPanel;
    }

    /**
     * Method that displays all the non expired items in the arraylist
     * @return - a JPanel that will then be passed onto a JScroll Pane in the mainGUI
     */
    public JPanel nonExpiredItems() {

        List<Consumable> list = new ArrayList<>();
        try {
            loadCertainItems(list, "http://localhost:8080/listNonExpired");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        JPanel mainPanel = new JPanel();
        Box box = Box.createVerticalBox();
        String type;

        if (list.isEmpty()) {
            JPanel noItems = new JPanel();
            JLabel text = new JLabel("No Items To Show");
            text.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            text.setHorizontalAlignment(JLabel.CENTER);
            noItems.setLayout(new GridLayout(1, 1));
            noItems.add(text);
            return noItems;
        } else {
            int counter = 1;
            for(Consumable i : list){
                if (i.getType() == 1) {
                    type = "(Food)";
                } else {
                    type = "(Drink)";
                }
                JLabel item = new JLabel("<html> <br> Item #"
                        + counter + " " + type + "<html>");
                item.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
                JLabel text = new JLabel(i.toString());
                text.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
                JLabel line1 = new JLabel("------------------------------------------------------");
                line1.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
                box.add(item);
                box.add(text);
                box.add(line1);
                counter++;
            }
        }
        mainPanel.add(box);
        return mainPanel;
    }

    /**
     * Method that displays all the items expiring in seven days
     * @return - a JPanel that will then be passed onto a JScroll Pane in the mainGUI
     */
    public JPanel inSevenDays() {

        List<Consumable> list = new ArrayList<>();
        try {
            loadCertainItems(list, "http://localhost:8080/listExpiringIn7Days");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        JPanel mainPanel = new JPanel();
        Box box = Box.createVerticalBox();
        String type;

        if (list.isEmpty()) {
            JPanel noItems = new JPanel();
            JLabel text = new JLabel("No Items To Show");
            text.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            text.setHorizontalAlignment(JLabel.CENTER);
            noItems.setLayout(new GridLayout(1, 1));
            noItems.add(text);
            return noItems;
        } else {
            int counter = 1;
            for(Consumable i : list){
                if (i.getType() == 1) {
                    type = "(Food)";
                } else {
                    type = "(Drink)";
                }
                JLabel item = new JLabel("<html> <br> Item #"
                        + counter + " " + type + "<html>");
                item.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
                JLabel text = new JLabel(i.toString());
                text.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
                JLabel line1 = new JLabel("------------------------------------------------------");
                line1.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
                box.add(item);
                box.add(text);
                box.add(line1);
                counter++;
            }
        }
        mainPanel.add(box);
        return mainPanel;
    }

    /**
     * Method that tells the server to save the items when the GUI closes
     * @throws IOException
     */
    public static void saveItems() throws IOException {
        URL url = new URL("http://localhost:8080/exit");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        }
    }

    /**
     * Method to load certain items (such as expired items, or non expired items, etc.)
     * @param list - the list the items will be loaded into
     * @param string - which types of items to laod (i.e. expired, non expired, etc.)
     * @throws IOException
     */
    public static void loadCertainItems(List<Consumable> list, String string) throws IOException {

        URL url = new URL(string);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();

        StringBuilder inline = new StringBuilder();
        Scanner scanner = new Scanner(url.openStream());

        while (scanner.hasNext()) {
            inline.append(scanner.nextLine());
        }
        scanner.close();

        JsonElement fileValue = JsonParser.parseString(inline.toString());
        JsonArray jsonArray = fileValue.getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject taskObj = jsonArray.get(i).getAsJsonObject();
            String taskDate = taskObj.get("expiryDate").getAsString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(taskDate, formatter);
            Consumable consumable = factory.getInstance(
                    taskObj.get("type").getAsInt(),
                    taskObj.get("name").getAsString(),
                    taskObj.get("notes").getAsString(),
                    taskObj.get("price").getAsDouble(),
                    taskObj.get("size").getAsDouble(),
                    localDateTime
            );
            list.add(consumable);
        }

    }

    /**
     * Method that loads the all the consumables from the server list into the client list
     * @throws IOException
     */
    public static void loadItems() throws IOException {

        if (!consumableList.isEmpty()) {
            consumableList.clear();
        }

        URL url = new URL("http://localhost:8080/listAll");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();

        StringBuilder inline = new StringBuilder();
        Scanner scanner = new Scanner(url.openStream());

        while (scanner.hasNext()) {
            inline.append(scanner.nextLine());
        }
        scanner.close();

        JsonElement fileValue = JsonParser.parseString(inline.toString());
        JsonArray jsonArray = fileValue.getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject taskObj = jsonArray.get(i).getAsJsonObject();
            String taskDate = taskObj.get("expiryDate").getAsString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(taskDate, formatter);
            Consumable consumable = factory.getInstance(
                    taskObj.get("type").getAsInt(),
                    taskObj.get("name").getAsString(),
                    taskObj.get("notes").getAsString(),
                    taskObj.get("price").getAsDouble(),
                    taskObj.get("size").getAsDouble(),
                    localDateTime
            );
            consumableList.add(consumable);
        }

    }

}
