import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;
public class ImageProcessing {
  public static void main(String[] args) {
    //ネットで拾ったプーチン大統領の画像を使用
    int[][] imageData = imgToTwoD("https://cdn.cfr.org/sites/default/files/image/2023/06/RussiaEU_HP_0.jpg");

    int[][] putin = negativeColor(imageData);
    
    putin = stretchHorizontally(putin);
    putin = shrinkVertically(putin);
    putin = invertImage(putin);
    System.out.println(putin.length);
    System.out.println(putin[0].length);
    putin = colorFilter(putin, 100, 25, -50);
    twoDToImage(putin, "./putin.jpeg");

    //ランダムなモザイク生成
    int[][] canvas = new int[500][500];
    paintRandomImage(canvas);
    twoDToImage(canvas, "./canvas.jpeg");
    int[] rgbaColor = {255, 255, 0, 255};
    canvas = paintRectangle(canvas, 100, 100, 200, 200, getColorIntValFromRGBA(rgbaColor));
   
    twoDToImage(canvas, "./canvas.jpeg");

		int[][] generatedRectangles = generateRectangles(canvas, 1000);

		twoDToImage(generatedRectangles, "./generated_rect.jpg");
  }


  // 以下、画像処理メソッド諸々
    //指定したピクセル数だけ画像の周囲を削るメソッド
  public static int[][] trimBorders(int[][] imageTwoD, int pixelCount) {
    if (imageTwoD.length > pixelCount * 2 && imageTwoD[0].length > pixelCount * 2) {
      int[][] trimmedImg = new int[imageTwoD.length - pixelCount * 2][imageTwoD[0].length - pixelCount * 2];
      for (int i = 0; i < trimmedImg.length; i++) {
        for (int j = 0; j < trimmedImg[i].length; j++) {
          trimmedImg[i][j] = imageTwoD[i + pixelCount][j + pixelCount];
        }
      }
      return trimmedImg;
    } else {
      System.out.println("そんなに削れないよ！");
      return imageTwoD;
    }
  }

  //色反転メソッド
  public static int[][] negativeColor(int[][] imageTwoD) {
    int[][] invertedImage = new int[imageTwoD.length][imageTwoD[0].length];
    for (int i = 0; i < imageTwoD.length; i++){
      for (int j = 0; j<imageTwoD[i].length; j++){
        int[] rgba = getRGBAFromPixel(imageTwoD[i][j]);
        for(int x = 0; x<rgba.length;x++){
          rgba[x] = 255-rgba[x];
        }
        invertedImage[i][j] = getColorIntValFromRGBA(rgba);
      }
    }
    return invertedImage;
  }

  //画像を水平方向に伸ばすメソッド
  public static int[][] stretchHorizontally(int[][] imageData) {
    int[][] stretchedImage = new int[imageData.length][2*imageData[0].length];
    for(int i = 0; i < imageData.length; i++){
      for(int j = 0; j < imageData[i].length; j++){
        int position = j*2;
        stretchedImage[i][position] = imageData[i][j];
        stretchedImage[i][position+1]=imageData[i][j];
      }
    }
    return stretchedImage;
  }

    //画像を垂直方向に縮めるメソッド
  public static int[][] shrinkVertically(int[][] imageData) {
    int[][] shrunkenImage = new int[imageData.length/2][imageData[0].length];
    for(int i = 0; i < imageData[0].length; i++){
      for(int j = 0; j < imageData.length-1; j+=2){
       
        shrunkenImage[j/2][i] = imageData[j][i];
        
      }
    }
    return shrunkenImage;
    
  }

    //反転メソッド
  public static int[][] invertImage(int[][] imageTwoD) {
    int[][] inverted = new int[imageTwoD.length][imageTwoD[0].length];
    for (int i = 0; i<imageTwoD.length;i++){
      for (int j = 0; j<imageTwoD[i].length;j++){
        inverted[i][j] = imageTwoD[(imageTwoD.length-1) - i][(imageTwoD[i].length-1)-j];
				
      }
    }
    return inverted;
  }

    //フィルターがけメソッド
  public static int[][] colorFilter(int[][] imageTwoD, int redChangeValue, int greenChangeValue, int blueChangeValue) {
    int[][] filtered = new int[imageTwoD.length][imageTwoD[0].length];
    for(int i = 0; i<imageTwoD.length; i++){
      for (int j = 0; j<imageTwoD[i].length; j++){
        int[] rgba = getRGBAFromPixel(imageTwoD[i][j]);
        rgba[0] += redChangeValue;
        rgba[1] += greenChangeValue;
        rgba[2] += blueChangeValue;
          if(rgba[0]>255){
            rgba[0]=255;
          } else if (rgba[0]<0){
            rgba[0]=0;
          }
          if(rgba[1]>255){
            rgba[1]=255;
          } else if (rgba[1]<0){
            rgba[1]=0;
          }
          if(rgba[2]>255){
            rgba[2]=255;
          } else if (rgba[2]<0){
            rgba[2]=0;
          }


        filtered[i][j] = getColorIntValFromRGBA(rgba);
      }
    }
    return filtered;
  }
  //以下、画像生成メソッド諸々

    //ランダムなモザイク画像生成メソッド
  public static int[][] paintRandomImage(int[][] canvas){
    
    Random rand = new Random();
    for(int i = 0; i<canvas.length;i++){
      for(int j = 0; j<canvas[i].length;j++){
        int[] rgba = {rand.nextInt(256), rand.nextInt(256), rand.nextInt(256), 255};
        canvas[i][j] = getColorIntValFromRGBA(rgba);
      }
    } return canvas;
  }
    
    //指定位置に指定色で四角形を描くメソッド
  public static int[][] paintRectangle(int[][] canvas, int width, int height, int rowPosition, int colPosition, int color) {
    int[][] changed = new int[canvas.length][canvas[0].length];
    for(int i = 0; i<canvas.length; i++){
      for(int j = 0; j<canvas[i].length;j++){
       if(i<rowPosition||i>rowPosition+height||j<colPosition||j>colPosition+width){
          changed[i][j] = canvas[i][j];
      } else {
        changed[i][j] = color;
      }
      }
    }
    return changed;
  }

    //位置・色をランダムに四角形を描くメソッド
  public static int[][] generateRectangles(int[][] canvas, int numRectangles) {
    Random rand = new Random();
    for(int i = 0; i<numRectangles; i++){
      int ranH = rand.nextInt(canvas.length-1);
      int ranW = rand.nextInt(canvas[i].length-1);
      int ranR = rand.nextInt(canvas.length-ranH);
      int ranC = rand.nextInt(canvas[i].length-ranW);
      int[] rgba = {rand.nextInt(256), rand.nextInt(256), rand.nextInt(256), 255};
      int ranCol = getColorIntValFromRGBA(rgba);
      canvas = paintRectangle(canvas, ranW, ranH, ranC, ranR, ranCol);
    }
    return canvas;
  }




  // Utility Methods
  public static int[][] imgToTwoD(String inputFileOrLink) {
    try {
      BufferedImage image = null;
      if (inputFileOrLink.substring(0, 4).toLowerCase().equals("http")) {
        URI uri = new URI(inputFileOrLink);
        URL imageUrl = uri.toURL();
        image = ImageIO.read(imageUrl);
        if (image == null) {
          System.out.println("そのURLからは画像を読み込めなかったよ！ごめんね！");
        }
      } else {
        image = ImageIO.read(new File(inputFileOrLink));
      }
      int imgRows = image.getHeight();
      int imgCols = image.getWidth();
      int[][] pixelData = new int[imgRows][imgCols];
      for (int i = 0; i < imgRows; i++) {
        for (int j = 0; j < imgCols; j++) {
          pixelData[i][j] = image.getRGB(j, i);
        }
      }
      return pixelData;
    } catch (Exception e) {
      System.out.println("画像を読み込めなかったよ！: " + e.getLocalizedMessage());
      return null;
    }
  }
  public static void twoDToImage(int[][] imgData, String fileName) {
    try {
      int imgRows = imgData.length;
      int imgCols = imgData[0].length;
      BufferedImage result = new BufferedImage(imgCols, imgRows, BufferedImage.TYPE_INT_RGB);
      for (int i = 0; i < imgRows; i++) {
        for (int j = 0; j < imgCols; j++) {
          result.setRGB(j, i, imgData[i][j]);
        }
      }
      File output = new File(fileName);
      ImageIO.write(result, "jpg", output);
    } catch (Exception e) {
      System.out.println("画像を保存できなかったよ！: " + e.getLocalizedMessage());
    }
  }
  public static int[] getRGBAFromPixel(int pixelColorValue) {
    Color pixelColor = new Color(pixelColorValue);
    return new int[] { pixelColor.getRed(), pixelColor.getGreen(), pixelColor.getBlue(), pixelColor.getAlpha() };
  }
  public static int getColorIntValFromRGBA(int[] colorData) {
    if (colorData.length == 4) {
      Color color = new Color(colorData[0], colorData[1], colorData[2], colorData[3]);
      return color.getRGB();
    } else {
      System.out.println("RGBA配列の長さが違うよ！");
      return -1;
    }
  }
  public static void viewImageData(int[][] imageTwoD) {
    if (imageTwoD.length > 3 && imageTwoD[0].length > 3) {
      int[][] rawPixels = new int[3][3];
      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          rawPixels[i][j] = imageTwoD[i][j];
        }
      }
      System.out.println("生の画素データ（左上から）");
      System.out.print(Arrays.deepToString(rawPixels).replace("],", "],\n") + "\n");
      int[][][] rgbPixels = new int[3][3][4];
      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          rgbPixels[i][j] = getRGBAFromPixel(imageTwoD[i][j]);
        }
      }
      System.out.println();
      System.out.println("RGBAの画素データ（左上から）");
      for (int[][] row : rgbPixels) {
        System.out.print(Arrays.deepToString(row) + System.lineSeparator());
      }
    } else {
      System.out.println("9ピクセル以上の画像を指定しないと見れないよ！");
    }
  }
}
