package com.api.pojo.addbook;

public class AddBook {
	
	

		
		private String name;	
		private String isbn;	
		private String aisle;	
		private String author;
		
		//private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
		
		public AddBook(String name, String isbn, String aisle, String author) {
			
			this.name = name;
			this.isbn = isbn;
			this.aisle = aisle;
			this.author = author;
		}
		

		
		public String getName() {
		return name;
		}
		public void setName(String name) {
		this.name = name;
		}		
		public String getIsbn() {
		return isbn;
		}	
		public void setIsbn(String isbn) {
		this.isbn = isbn;
		}	
		public String getAisle() {
		return aisle;
		}
		public void setAisle(String aisle) {
		this.aisle = aisle;
		}
		public String getAuthor() {
		return author;
		}
		public void setAuthor(String author) {
		this.author = author;
		}

		
		/*public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
		}

		
		public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
		}*/

		

}
