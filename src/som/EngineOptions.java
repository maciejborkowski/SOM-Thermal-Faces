package som;

public class EngineOptions {
	private String pictures;
	private float learningRateFrom;
	private float learningRateTo;
	private float mexicanHatParamTo;
	private float mexicanHatParamFrom;
	private int layerX;
	private int layerY;
	private int loops;

	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}

	public int getLayerX() {
		return layerX;
	}

	public void setLayerX(int layerX) {
		this.layerX = layerX;
	}

	public int getLayerY() {
		return layerY;
	}

	public void setLayerY(int layerY) {
		this.layerY = layerY;
	}

	public int getLoops() {
		return loops;
	}

	public void setLoops(int loops) {
		this.loops = loops;
	}

	public float getMexicanHatParamTo() {
		return mexicanHatParamTo;
	}

	public void setMexicanHatParamTo(float mexicanHatParamTo) {
		this.mexicanHatParamTo = mexicanHatParamTo;
	}

	public float getMexicanHatParamFrom() {
		return mexicanHatParamFrom;
	}

	public void setMexicanHatParamFrom(float mexicanHatParamFrom) {
		this.mexicanHatParamFrom = mexicanHatParamFrom;
	}

	public float getLearningRateTo() {
		return learningRateTo;
	}

	public void setLearningRateTo(float learningRateTo) {
		this.learningRateTo = learningRateTo;
	}

	public float getLearningRateFrom() {
		return learningRateFrom;
	}

	public void setLearningRateFrom(float learningRateFrom) {
		this.learningRateFrom = learningRateFrom;
	}

}
