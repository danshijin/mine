import java.util.Date;
import java.util.Random;

public class Password {

	private static int length = 16; //密码长度

	public static byte[] sourceArr = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
			'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
			'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', '$', '@', '_', '!' }; // 密码可选字符

	public static void main(String[] args) {
		Random r = new Random(new Date().getTime());
		for (int i = 0; i < length; i++) {
			int n = r.nextInt(sourceArr.length);
			System.out.print((char) sourceArr[n]); // 打印密码
		}

	}
}