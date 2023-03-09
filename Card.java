public class Card {
    private String element;
    private int powerNumber;
    private String color;
  
    public Card(String element, int powerNumber, String color) {
      this.element = element;
      this.powerNumber = powerNumber;
      this.color = color;
    }
  
    public String getElement() {
      return element;
    }
  
    public int getPowerNumber() {
      return powerNumber;
    }
  
    public String getColor() {
      return color;
    }
  }