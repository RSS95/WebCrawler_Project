package in.webcrawler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class WebCrawlerApplication {

	public static void main(String[] args) {

		WebCrawlerApplication wc = new WebCrawlerApplication();
		
		WebClient client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);

		HtmlPage page = null;

		try {
			String searchUrl = "https://en.wikipedia.org/wiki/India"; /* + URLEncoder.encode(searchQuery, "UTF-8") */
			page = client.getPage(searchUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}

		wc.writeFile("out.txt", page.asXml(), "D:\\Workspace\\Code_Workspace\\WebCrawler\\WebCrawler.git\\output"); 
		
		// System.out.println(page.asXml());

		List<HtmlAnchor> items = page.getAnchors();
		
		List<HtmlAnchor> trimList = new ArrayList<HtmlAnchor>();
		
		String s = "";
		for(HtmlAnchor item : items) {
			if(!item.getHrefAttribute().equals("")) {
				trimList.add(item);
				s = s.concat(item.toString() + "\n");
				System.out.println("First :: " + item.asText());
				System.out.println("Second :: " + item.getHrefAttribute());
				System.out.println("Third :: " + item.getAttribute("title"));
			}
		}
		
		wc.writeFile("anchors.txt", s, "D:\\Workspace\\Code_Workspace\\WebCrawler\\WebCrawler.git\\output"); 
		
		if (items.isEmpty()) {
			System.out.println("No items found !");
		} else {

			System.out.println("Total <a> Found :: " + items.size());
			
			for (HtmlElement item : items) {
				
				HtmlAnchor itemAnchor = ((HtmlAnchor) item.getFirstByXPath(".//p[@class='result-info']/a"));

				HtmlElement spanPrice = ((HtmlElement) item.getFirstByXPath(".//a/span[@class='result-price']"));

				String itemName = itemAnchor.asText();
				String itemUrl = itemAnchor.getHrefAttribute();

				// It is possible that an item doesn't have any price
				String itemPrice = spanPrice == null ? "0.0" : spanPrice.asText();

				System.out.println(String.format("Name : %s Url : %s Price : %s", itemName, itemPrice, itemUrl));
			}
		}

		client.close();
	}
	
	public void writeFile(String name, String data, String path) {
		
		try {
			//File file = new File("D:\\Workspace\\Code_Workspace\\WebCrawler\\WebCrawler.git\\output\\out.txt");

			File file = new File(path + "\\" + name);
			
			// Create the file
			if (file.createNewFile()) {
				System.out.println("File is created!");
			} else {
				System.out.println("File already exists.");
			}

			// Write Content
			FileWriter writer = new FileWriter(file);
			writer.write(data);
			writer.close();
		} catch (Exception e) {
		}
	}
}
