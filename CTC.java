

public class CTC{
    private String name1;
    private String name2;
    private int cn1;//city number
    private int cn2;
    private double price;
    private double distance;
    public CTC(String name1,String name2,int cn1,int cn2,double distance,double price){
        this.cn1=cn1;
        this.cn2=cn2;
        this.name1=name1;
        this.name2=name2;
        this.price=price;
        this.distance=distance;
    }


    public String getName1() {
        return name1;
    }


    public void setName1(String name1) {
        this.name1 = name1;
    }


    public String getName2() {
        return name2;
    }


    public void setName2(String name2) {
        this.name2 = name2;
    }


    public int getCn1() {
        return cn1;
    }


    public void setCn1(int cn1) {
        this.cn1 = cn1;
    }


    public int getCn2() {
        return cn2;
    }


    public void setCn2(int cn2) {
        this.cn2 = cn2;
    }



    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public double getDistance() {
        return distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }
    
}