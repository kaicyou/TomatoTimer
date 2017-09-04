
public class SetTimeFormat {
	public static String setTimeFormat(int hour, int minute, int sec, boolean needSec) {
		StringBuilder sb = new StringBuilder();
		if (hour < 10) {
			sb.append(0);
		}
		sb.append(hour);
		sb.append(':');
		if (minute < 10) {
			sb.append(0);
		}
		sb.append(minute);
		if (needSec) {
			sb.append(':');
			if (sec < 10) {
				sb.append(0);
			}
			sb.append(sec);
		}
		return sb.toString();
	}
}
