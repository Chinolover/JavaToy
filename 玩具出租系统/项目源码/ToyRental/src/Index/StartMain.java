package Index;

public class StartMain {

	public static Login Get() throws Exception{
		return new Login();
	}

	//工厂设计模式
	public static void main(String[] args) throws Exception {
		StartMain.Get();
	}

}
