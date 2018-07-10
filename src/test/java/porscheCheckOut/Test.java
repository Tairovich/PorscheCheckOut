package porscheCheckOut;

import org.openqa.selenium.By;

public class Test {

	public static long priceConverted(String str) {
		char[] chars = str.toCharArray();
		String price ="";
		
		A:
		for (int i = 0; i < chars.length; i++) {
			if(chars[i] == '.') {
				break A;
			}
			
			if(Character.isDigit(chars[i])) {
				price += chars[i];
			}
		}
		long priceInt = Long.parseLong(price);
		
		return priceInt;
	}
	public static void main(String[] args) {
		
		long price1 = priceConverted("$56900.00");
		
		String basePrice = "$56,900";
		
		long price2 = priceConverted(basePrice);
		
		if(price1 == price2) {
			System.out.println("pass");
		}else {
			System.out.println("fail");
		}
		
		String EqPrice = "$0".replace("$", "");
		int equip = Integer.parseInt(EqPrice);
		if(equip == 0) {
			System.out.println("Price for Equipment is 0");
		}else {
			System.out.println("Price for Equipment is not 0");
		}
		
		
		
	}

}
