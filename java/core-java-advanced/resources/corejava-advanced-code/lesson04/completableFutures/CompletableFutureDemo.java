package completableFutures;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public class CompletableFutureDemo {	
	private ExecutorService executor = Executors.newCachedThreadPool();
	private static final Pattern IMG_PATTERN = Pattern.compile(
			"[<]\\s*[iI][mM][gG]\\s*[^>]*[sS][rR][cC]\\s*[=]\\s*['\"]([^'\"]*)['\"][^>]*[>]");
	private URL urlToProcess;
	public static byte[] readAllBytes(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		copy(in, out);
		out.close();
		return out.toByteArray();
	}
	
	public static void copy(InputStream in, OutputStream out) throws IOException {
		final int BLOCKSIZE = 1024;
		byte[] bytes = new byte[BLOCKSIZE];
		int len;
		while ((len = in.read(bytes)) != -1) out.write(bytes, 0, len);
	}
	
	public static String readString(InputStream in) throws IOException {
		return new String(readAllBytes(in), "UTF-8");
	}		
	
	public CompletableFuture<String> readPage(URL url) {
		return CompletableFuture.supplyAsync(() -> {
				try {
					String contents = readString(url.openStream());
					System.out.println("Read page from " + url);
					return contents;
				} catch (IOException ex) {
					throw new UncheckedIOException(ex);
				}
			}, executor);			
	}

	public List<URL> getImageURLs(String webpage) { // Not time-consuming
		try {
			List<URL> result = new ArrayList<>();
			Matcher matcher = IMG_PATTERN.matcher(webpage);
			while (matcher.find()) {
				URL url = new URL(urlToProcess, matcher.group(1));
				result.add(url); 
			}
			System.out.println("Found URLs: " + result);
			return result;
		} catch (IOException ex) {
			throw new UncheckedIOException(ex);
		}		
	}
	
	public CompletableFuture<List<BufferedImage>> getImages(List<URL> urls) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				List<BufferedImage> result = new ArrayList<>();
				for (URL url : urls) {
					result.add(ImageIO.read(url));
					System.out.println("Loaded " + url);
				}		
				return result;
			} catch (IOException ex) {
				throw new UncheckedIOException(ex);
			}
		}, executor);
	}
	
	public void saveImages(List<BufferedImage> images) {
		System.out.println("Saving " + images.size() + " images");
		try {
			for (int i = 0; i < images.size(); i++) { 
				String filename = "/tmp/image" + (i + 1) + ".png";
				ImageIO.write(images.get(i), "PNG", new File(filename));
			}
		} catch (IOException ex) {
			throw new UncheckedIOException(ex);
		}
		executor.shutdown();
	}
	
	public void run() throws IOException, InterruptedException {
		urlToProcess = new URL("http://horstmann.com/index.html");
		CompletableFuture.completedFuture(urlToProcess)
			.thenComposeAsync(this::readPage, executor)
			.thenApply(this::getImageURLs)
			.thenCompose(this::getImages)
			.thenAccept(this::saveImages);
	}

	public static void main(String[] args) throws Exception {
		new CompletableFutureDemo().run();
	}
}