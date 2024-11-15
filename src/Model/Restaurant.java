package Model;

public class Restaurant {
	 private String name;
     private String address;
     private String discount;
     private String category;
     private String priceRange;
     private String imagePath;

     public Restaurant(String name, String address, String discount, String category, String priceRange, String imagePath) {
         this.name = name;
         this.address = address;
         this.discount = discount;
         this.category = category;
         this.priceRange = priceRange;
         this.imagePath = imagePath;
     }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
     
}
