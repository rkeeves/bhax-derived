package org.rkeeves.backprop;

import java.util.Arrays;
import java.util.Random;

public class NNet {

	public class Matrix {
		
		public Matrix(int rows, int cols)
		{
			this.cols = cols;
			this.values =  new Random().doubles(rows*cols, -1.0, 1.0).toArray();
		}
		
		public void transform(double[] from, double[] to) {
			Arrays.fill(to, 0.0);
			int row;
			int col;
			for (int i = 0; i < values.length; i++) {
				row = i / cols;
				col = i % cols;
				to[row]+=values[row*cols+col]*from[col];
			}
			for (int i = 0; i < to.length; i++) {
				to[i] = sigmoid(to[i]);
			}
		}
		
		private final int cols;
		private final double values[];
	}
	
	public static final double sigmoid(double z) {
		return 1.0/(1.0+Math.exp(-z));
	}
	
	public NNet(int[] vectors_sizes) {
		vectors = new double[vectors_sizes.length][];
		for (int i = 0; i < vectors_sizes.length; i++) {
			vectors[i] = new double[vectors_sizes[i]];
		}
		transforms = new Matrix[vectors_sizes.length-1];
		for (int i = 1; i < vectors_sizes.length; i++) {
			transforms[i-1] = new Matrix(vectors_sizes[i], vectors_sizes[i-1]);
		}
	}
	
	public double[] compute(double[] input) {
		vectors[0] = input;
		for (int i = 0; i < transforms.length; i++) {
			transforms[i].transform(vectors[i], vectors[i+1]);
		}
		return vectors[vectors.length-1];
	}
	
	private final double[][] vectors;
	private final Matrix[] transforms;
	
	public static void main(String[] args) {
		System.out.println(
				Arrays.toString(
						new NNet(new int[] {3,2,1})
							.compute(new double[] {0.1,-0.5,0.1})));
	}
}
