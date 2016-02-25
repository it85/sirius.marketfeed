package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import contract.IMarketFeed;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class MarketFeed implements IMarketFeed{

	String symbol;
	
	public MarketFeed(String symbol){
		this.symbol = symbol;
	}
	
	public CandleStick getCandleStick() throws IOException{
		
		Stock stock = YahooFinance.get(this.symbol);
		BigDecimal price = stock.getQuote(true).getPrice();
		
		
		return null;
	}
	
	public BigDecimal getPrice() throws IOException{
		Stock stock = YahooFinance.get(this.symbol);
		return stock.getQuote(true).getPrice();
	}
	
	@Override
	public List<CandleStick> staticFeedAsList(String filePath) throws FileNotFoundException {
		
		if(!fileExists(filePath)){
			throw new FileNotFoundException();
		}
		
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		
		ArrayList<CandleStick> candleStickList = new ArrayList<CandleStick>();
		
		try {
			
			br = new BufferedReader(new FileReader(filePath));
			line = br.readLine();
				
			while ((line = br.readLine()) != null) {
				String[] arr = line.split(cvsSplitBy);
				CandleStick candle = new CandleStick(arr);
				candleStickList.add(candle);
			}
	
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
				br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}		
		return candleStickList;
	}
	
	private boolean fileExists(String filePath){
		return false;
	}

}
