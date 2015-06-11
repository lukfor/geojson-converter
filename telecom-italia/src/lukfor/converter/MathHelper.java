package lukfor.converter;

public class MathHelper {

	static public float map(float value, float min, float max, float x1,
			float x2) {

		return (x1 + (x2 - x1) * ((value - min) / (max - min)));
	}

	static public final float map2(float value, float istart, float istop,
			float ostart, float ostop) {
		
		if (value > istop){
			return ostop;
		}
		
		if (value < istart){
			return ostart;
		}
		
		return ostart + (ostop - ostart)
				* ((value - istart) / (istop - istart));
	}

}
