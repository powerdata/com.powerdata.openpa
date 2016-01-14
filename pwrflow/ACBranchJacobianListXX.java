package com.powerdata.openpa.pwrflow;

import com.powerdata.openpa.PAModelException;

public interface ACBranchJacobianListXX extends ACBranchExtList<com.powerdata.openpa.pwrflow.ACBranchJacobianListXX.ACBranchJacobianXX>
{
	static public class ACBranchJacobianXX extends ACBranchExtList.ACBranchExt
	{
		ACBranchJacobianListXX _list;
		
		public ACBranchJacobianXX(ACBranchJacobianListXX list, int index)
		{
			super(list, index);
			_list = list;
		}
		/**
		 * partial derivative of from-side active power with respect to
		 * from-side voltage magnitude
		 */
		public float getdFPdFVm() {return _list.getdFPdFVm(_ndx);}
		/**
		 * partial derivative of from-side active power w.r.t.
		 * to-side voltage magnitude
		 */
		public float getdFPdTVm() {return _list.getdFPdTVm(_ndx);}
		/**
		 * partial derivative of from-side active power w.r.t.
		 * from-side voltage angle
		 */
		public float getdFPdFVa() {return _list.getdFPdFVa(_ndx);}
		/**
		 * partial derivative of from-side active power w.r.t. to-side voltage
		 * angle
		 */
		public float getdFPdTVa() {return _list.getdFPdTVa(_ndx);}
		/**
		 * partial derivative of from-side reactive power w.r.t. from-side voltage magnitude
		 */
		public float getdFQdFVm() {return _list.getdFQdFVm(_ndx);}
		/**
		 * partial derivative of from-side reactive power w.r.t. to-side voltage magnitude
		 */
		public float getdFQdTVm() {return _list.getdFQdTVm(_ndx);}
		/**
		 * partial derivative of from-side reactive power w.r.t from-side voltage angle
		 */
		public float getdFQdFVa() {return _list.getdFQdFVa(_ndx);}
		/**
		 * partial derivative of from-side reactive power w.r.t to-side voltage angle
		 */
		public float getdFQdTVa() {return _list.getdFQdTVa(_ndx);}
		
		
		
		
		/**
		 * partial derivative of to-side active power with respect to
		 * from-side voltage magnitude
		 */
		public float getdTPdFVm() {return _list.getdTPdFVm(_ndx);}
		/**
		 * partial derivative of to-side active power w.r.t.
		 * to-side voltage magnitude
		 */
		public float getdTPdTVm() {return _list.getdTPdTVm(_ndx);}
		/**
		 * partial derivative of to-side active power w.r.t.
		 * from-side voltage angle
		 */
		public float getdTPdFVa() {return _list.getdTPdFVa(_ndx);}
		/**
		 * partial derivative of to-side active power w.r.t. to-side voltage
		 * angle
		 */
		public float getdTPdTVa() {return _list.getdTPdTVa(_ndx);}
		/**
		 * partial derivative of to-side reactive power w.r.t. from-side voltage magnitude
		 */
		public float getdTQdFVm() {return _list.getdTQdFVm(_ndx);}
		/**
		 * partial derivative of to-side reactive power w.r.t. to-side voltage magnitude
		 */
		public float getdTQdTVm() {return _list.getdTQdTVm(_ndx);}
		/**
		 * partial derivative of to-side reactive power w.r.t from-side voltage angle
		 */
		public float getdTQdFVa() {return _list.getdTQdFVa(_ndx);}
		/**
		 * partial derivative of to-side reactive power w.r.t to-side voltage angle
		 */
		public float getdTQdTVa() {return _list.getdTQdTVa(_ndx);}

	}
	
	void calc(float[] vmpu, float[] varad) throws PAModelException;

	/**
	 * partial derivative of from-side active power with respect to
	 * from-side voltage magnitude
	 */
	float getdFPdFVm(int ndx);
	/**
	 * partial derivative of from-side active power with respect to
	 * from-side voltage magnitude
	 */
	float[] getdFPdFVm();
	/**
	 * partial derivative of from-side active power with respect to
	 * to-side voltage magnitude
	 */
	float getdFPdTVm(int ndx);
	/**
	 * partial derivative of from-side active power with respect to
	 * to-side voltage magnitude
	 */
	float[] getdFPdTVm();
	/**
	 * partial derivative of from-side active power with respect to
	 * from-side voltage angle
	 */
	float getdFPdFVa(int ndx);
	/**
	 * partial derivative of from-side active power with respect to
	 * from-side voltage angle
	 */
	float[] getdFPdFVa();
	/**
	 * partial derivative of from-side active power w.r.t. to-side voltage
	 * angle
	 */
	float getdFPdTVa(int ndx);
	/**
	 * partial derivative of from-side active power w.r.t. to-side voltage
	 * angle
	 */
	float[] getdFPdTVa();
	/**
	 * partial derivative of from-side reactive power w.r.t. from-side voltage magnitude
	 */
	float getdFQdFVm(int ndx);
	/**
	 * partial derivative of from-side reactive power w.r.t. from-side voltage magnitude
	 */
	float[] getdFQdFVm();
	/**
	 * partial derivative of from-side reactive power w.r.t. to-side voltage magnitude
	 */
	float getdFQdTVm(int ndx);
	/**
	 * partial derivative of from-side reactive power w.r.t. to-side voltage magnitude
	 */
	float[] getdFQdTVm();
	/**
	 * partial derivative of from-side reactive power w.r.t from-side voltage angle
	 */
	float getdFQdFVa(int ndx);
	/**
	 * partial derivative of from-side reactive power w.r.t from-side voltage angle
	 */
	float[] getdFQdFVa();
	/**
	 * partial derivative of from-side reactive power w.r.t to-side voltage angle
	 */
	float getdFQdTVa(int ndx);
	/**
	 * partial derivative of from-side reactive power w.r.t to-side voltage angle
	 */
	float[] getdFQdTVa();
	
	
	
	
	
	/**
	 * partial derivative of to-side active power with respect to
	 * from-side voltage magnitude
	 */
	float getdTPdFVm(int ndx);
	/**
	 * partial derivative of to-side active power with respect to
	 * from-side voltage magnitude
	 */
	float[] getdTPdFVm();
	/**
	 * partial derivative of to-side active power with respect to
	 * to-side voltage magnitude
	 */
	float getdTPdTVm(int ndx);
	/**
	 * partial derivative of to-side active power with respect to
	 * to-side voltage magnitude
	 */
	float[] getdTPdTVm();
	/**
	 * partial derivative of to-side active power with respect to
	 * from-side voltage angle
	 */
	float getdTPdFVa(int ndx);
	/**
	 * partial derivative of to-side active power with respect to
	 * from-side voltage angle
	 */
	float[] getdTPdFVa();
	/**
	 * partial derivative of to-side active power w.r.t. to-side voltage
	 * angle
	 */
	float getdTPdTVa(int ndx);
	/**
	 * partial derivative of to-side active power w.r.t. to-side voltage
	 * angle
	 */
	float[] getdTPdTVa();
	/**
	 * partial derivative of to-side reactive power w.r.t. from-side voltage magnitude
	 */
	float getdTQdFVm(int ndx);
	/**
	 * partial derivative of to-side reactive power w.r.t. from-side voltage magnitude
	 */
	float[] getdTQdFVm();
	/**
	 * partial derivative of to-side reactive power w.r.t. to-side voltage magnitude
	 */
	float getdTQdTVm(int ndx);
	/**
	 * partial derivative of to-side reactive power w.r.t. to-side voltage magnitude
	 */
	float[] getdTQdTVm();
	/**
	 * partial derivative of to-side reactive power w.r.t from-side voltage angle
	 */
	float getdTQdFVa(int ndx);
	/**
	 * partial derivative of to-side reactive power w.r.t from-side voltage angle
	 */
	float[] getdTQdFVa();
	/**
	 * partial derivative of to-side reactive power w.r.t to-side voltage angle
	 */
	float getdTQdTVa(int ndx);
	/**
	 * partial derivative of to-side reactive power w.r.t to-side voltage angle
	 */
	float[] getdTQdTVa();

}
