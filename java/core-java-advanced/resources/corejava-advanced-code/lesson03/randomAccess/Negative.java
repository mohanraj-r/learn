package randomAccess;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Negative {
	
	public static int getLittleEndianInt(RandomAccessFile file, long offset) throws IOException {
		file.seek(offset);
		int result = 0;
		int base = 1;
		for (int i = 0; i < 4; i++) {
			result = result + file.read() * base;
			base = base * 256;
		}
		return result;
	}

	public static void main(String[] args) throws IOException {
		try (RandomAccessFile imageFile = new RandomAccessFile("cat.bmp", "rw")) {;
			int fileSize = getLittleEndianInt(imageFile, 2); // Get the image dimensions
			int start = getLittleEndianInt(imageFile, 10);
			int width = getLittleEndianInt(imageFile, 18);
			int height = getLittleEndianInt(imageFile, 22);
			int scanlineSize = width * 3;
			int padding = scanlineSize % 4 == 0 ? 0 : 4 - scanlineSize % 4;
	
			if (fileSize != start + (scanlineSize + padding) * height)
				throw new IOException("Not a 24-bit true color image file");
			imageFile.seek(start);
			for (int i = 0; i < height; i++) { // For each scan line
				for (int j = 0; j < width; j++) { // For each pixel
					long pos = imageFile.getFilePointer(); // Go to the start of the pixel
					int blue = imageFile.read(); // Read the pixel
					int green = imageFile.read();
					int red = imageFile.read();
					imageFile.seek(pos); // Go back to the start of the pixel
				
					imageFile.write(255 - blue); // Write the pixel
					imageFile.write(255 - green);
					imageFile.write(255 - red);
				 }
				
				imageFile.seek(imageFile.getFilePointer() + padding); // Skip the padding
			}
		}
	}
}