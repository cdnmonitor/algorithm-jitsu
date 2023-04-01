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

    public String getString() {
      return this.element + "," + this.powerNumber + "," + this.color;
    }

    //Make sure the comparisons of cards check all values
    public boolean equals(Card o) {
      if (!this.element.equals(o.getElement())) {
        return false;
      }
      if (this.powerNumber != o.getPowerNumber()) {
        return false;
      }
      if (!this.color.equals(o.getColor())){
        return false;
      }
      return true;
    }
  }