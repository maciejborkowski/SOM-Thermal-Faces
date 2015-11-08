package som;

public class EngineOptions {
	private String pictures;
	private float learningRate;
	private float mexicanHatParam;
	private int layerX;
	private int layerY;

	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}

	public float getLearningRate() {
		return learningRate;
	}

	public void setLearningRate(float learningRate) {
		this.learningRate = learningRate;
	}

	public float getMexicanHatParam() {
		return mexicanHatParam;
	}

	public void setMexicanHatParam(float mexicanHatParam) {
		this.mexicanHatParam = mexicanHatParam;
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
}
