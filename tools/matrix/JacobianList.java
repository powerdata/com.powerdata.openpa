package com.powerdata.openpa.tools.matrix;

import java.util.List;

/**
 * List of Jacobian Elements. 
 * 
 * @author chris@powerdata.com
 *
 */
public interface JacobianList extends List<JacobianElement>
{
	static class Element implements com.powerdata.openpa.tools.matrix.JacobianElement
	{
		int _ndx;
		JacobianList _list;
		
		Element(JacobianList l, int ndx) {_ndx = ndx; _list=l;}
		@Override
		public float getDpda() {return _list.getDpda(_ndx);}
		@Override
		public float getDpdv() {return _list.getDpdv(_ndx);}
		@Override
		public float getDqda() {return _list.getDqda(_ndx);}
		@Override
		public float getDqdv() {return _list.getDqdv(_ndx);}
		@Override
		public void setDpda(float v) {_list.setDpda(_ndx, v);}
		@Override
		public void setDpdv(float v) {_list.setDpdv(_ndx, v);}
		@Override
		public void setDqda(float v) {_list.setDqda(_ndx, v);}
		@Override
		public void setDqdv(float v) {_list.setDqdv(_ndx, v);}
		@Override
		public void incDpda(float v) {_list.incDpda(_ndx, v);}
		@Override
		public void incDpdv(float v) {_list.incDpdv(_ndx, v);}
		@Override
		public void incDqda(float v) {_list.incDqda(_ndx, v);}
		@Override
		public void incDqdv(float v) {_list.incDqdv(_ndx, v);}
		@Override
		public void decDpda(float v) {_list.decDpda(_ndx, v);}
		@Override
		public void decDpdv(float v) {_list.decDpdv(_ndx, v);}
		@Override
		public void decDqda(float v) {_list.decDqda(_ndx, v);}
		@Override
		public void decDqdv(float v) {_list.decDqdv(_ndx, v);}
		@Override
		public void inc(JacobianElement e) {_list.inc(_ndx, e);}
		@Override
		public void dec(JacobianElement e) {_list.dec(_ndx, e);}
		@Override
		public String toString()
		{
			return String.format("[dpda:%f,dpdv:%f,dqda:%f,dqdv:%f]",
				getDpda(), getDpdv(), getDqda(), getDqdv());
		}
	}
	
	@Override
	default JacobianElement get(int index)
	{
		return new Element(this, index);
	}

	/** get the partial derivative of active power with respect to voltage angle */
	float getDpda(int ndx);
	/** get the partial derivative of active power with respect to voltage magnitude */
	float getDpdv(int ndx);
	/** get the partial derivative of reactive power with respect to voltage angle */
	float getDqda(int ndx);
	/** get the partial derivative of reactive power with respect to voltage magnitude */
	float getDqdv(int ndx);
	/** set the partial derivative of active power with respect to voltage angle */
	void setDpda(int ndx, float v);
	/** set the partial derivative of active power with respect to voltage magnitude */
	void setDpdv(int ndx, float v);
	/** set the partial derivative of reactive power with respect to voltage angle */
	void setDqda(int ndx, float v);
	/** set the partial derivative of reactive power with respect to voltage magnitude */
	void setDqdv(int ndx, float v);
	/** increment the partial derivative of active power with respect to voltage angle */
	default void incDpda(int ndx, float v) { setDpda(ndx, getDpda(ndx)+v); }
	/** increment the partial derivative of active power with respect to voltage magnitude */
	default void incDpdv(int ndx, float v) { setDpdv(ndx, getDpdv(ndx)+v); }
	/** increment the partial derivative of reactive power with respect to voltage angle */
	default void incDqda(int ndx, float v) { setDqda(ndx, getDqda(ndx)+v); }
	/** increment the partial derivative of reactive power with respect to voltage magnitude */
	default void incDqdv(int ndx, float v) { setDqdv(ndx, getDqdv(ndx)+v); }

	/** decrement the partial derivative of active power with respect to voltage angle */
	default void decDpda(int ndx, float v) { incDpda(ndx, -v); }
	/** decrement the partial derivative of active power with respect to voltage magnitude */
	default void decDpdv(int ndx, float v) { incDpdv(ndx, -v); }
	/** decrement the partial derivative of reactive power with respect to voltage angle */
	default void decDqda(int ndx, float v) { incDqda(ndx, -v); }
	/** decrement the partial derivative of reactive power with respect to voltage magnitude */
	default void decDqdv(int ndx, float v) { incDqdv(ndx, -v); }

	/** Increment all partial derivatives of this element with the given element */
	default void inc(int ndx, JacobianElement e)
	{
		incDpda(ndx, e.getDpda());
		incDpdv(ndx, e.getDpdv());
		incDqda(ndx, e.getDqda());
		incDqdv(ndx, e.getDqdv());
	}
	
	/** Deccrement all partial derivatives of this element with the given element */
	default void dec(int ndx, JacobianElement e)
	{
		decDpda(ndx, e.getDpda());
		decDpdv(ndx, e.getDpdv());
		decDqda(ndx, e.getDqda());
		decDqdv(ndx, e.getDqdv());
	}
	
	/**
	 * reset all the elements to 0
	 */
	void reset();
	
}
