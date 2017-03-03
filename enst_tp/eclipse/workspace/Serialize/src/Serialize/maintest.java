package Serialize;

public class maintest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HelloData data = new HelloData();
		data.set("messageTest", "transientMessageTest");
		DataSerializer ds = new DataSerializer(data);
		DataUnserializer du = new DataUnserializer();
		ds.start();
		du.start();

	}

}
