package mepo.Components;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mepo.Helper.CardHelper;
import mepo.Helper.ProductHelper;
import mepo.Implements.CardImp;
import mepo.Implements.ProductImp;

import java.io.*;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

public class Helper {

    public static String formatString(double d) {
        return new DecimalFormat("#").format(d);
    }

    public static void writeFile(List<Card> list) throws IOException {
        String filePath = new File("").getAbsolutePath().concat("\\src\\mepo\\cardCode.txt");
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
            file.createNewFile();
        }
        for (Card cardItem : list) {
            saveToFile( cardItem.toString(), true);
        }
    }

    private static void saveToFile(String text, boolean append) throws IOException {

        String filePath = new File("").getAbsolutePath().concat("\\src\\mepo\\cardCode.txt");
        File file = new File(filePath);
        FileWriter fileWriter = new FileWriter(file, append);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(text);
        printWriter.close();

    }

    public static int getRandomValue() {
        Random rand = new Random();
        int randomNum = rand.nextInt((3 - 1) + 1) + 1;
        return randomNum * 100;
    }

    public static int random6digitsCard() {
        return new Random().nextInt(900000) + 100000;
    }

    public static boolean checkCodeExist(int code, List<Integer> list) {
        for (Integer codeUsed : list) {
            if (codeUsed == code) {
                return true;
            }
        }
        return false;
    }

    public static void createCardCodeAutomatic() throws IOException {
        CardImp cardHelper = new CardHelper();
        Helper.writeFile(cardHelper.selectAll());
        Boolean flag = true;
        List<Integer> list = cardHelper.selectCodeAll();
        while (list.size() <= 100) {
            int code = Helper.random6digitsCard();
            if (!Helper.checkCodeExist(code, list)) {
                list.add(code);
                int value = Helper.getRandomValue();
                Card card = new Card();
                card.setCode(code);
                card.setValue(value);
                card.setUsed(false);
                cardHelper.insert(card);
            }

        }
    }

        public static void importDataMovie(){
        ProductImp productImp = new ProductHelper();
        String filePath = new File("").getAbsolutePath().concat("\\src\\mepo\\phim.txt");
        String filePathImg = new File("").getAbsolutePath().concat("\\src\\imgData\\");
        File file = new File(filePath);
        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = br.readLine()) != null) {
                String[] list = line.split("#");
                System.out.println(list[4]);
                Product product = new Product();
                product.setProductName(list[0]);
                product.setImage(Helper.convertImageToBytes(filePathImg+list[1]));
                product.setDescription(list[2]);
                product.setCategoryID(Integer.parseInt(list[3]));
                product.setPrice(list[4]);
                product.setVideo(list[5]);
                product.setUrl(list[6]);
                productImp.insert(product);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] convertImageToBytes(String filePath) throws IOException {
        File file = new File(filePath);
        return Files.readAllBytes(file.toPath());
    }

    public static Image convertByteToImage(byte[] bytes, ImageView imageView) {
        Image img = new Image(new ByteArrayInputStream(bytes));
        Canvas canvas = new Canvas();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(img, imageView.getFitWidth(), imageView.getFitHeight());
        return img;
    }
    public static String formatDate(LocalDate localDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDate.format(formatter);
    }

}
