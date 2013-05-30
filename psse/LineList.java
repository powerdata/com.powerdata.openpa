package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.PAMath;

public abstract class LineList extends PsseBaseList<Line>
{
	public LineList(PsseModel model) {super(model);}

	/* Standard object retrieval */

	/** Get a NontransformerBranch by it's index. */
	@Override
	public Line get(int ndx) { return new Line(ndx,this); }
	/** Get a NontransformerBranch by it's ID. */
	@Override
	public Line get(String id) { return super.get(id); }

	/* Convenience Methods */
	
	public abstract Bus getFromBus(int ndx) throws PsseModelException;
	public abstract Bus getToBus(int ndx)  throws PsseModelException;
	public abstract LineMeterEnd getMeteredEnd(int ndx) throws PsseModelException;
	public abstract boolean getInSvc(int ndx) throws PsseModelException;
	public abstract float getR100(int ndx) throws PsseModelException;
	public abstract float getX100(int ndx) throws PsseModelException;
	public abstract Complex getZ100(int ndx) throws PsseModelException;
	public abstract float getFromShuntG(int ndx) throws PsseModelException;
	public abstract float getFromShuntB(int ndx) throws PsseModelException;
	public abstract Complex getFromShuntY(int ndx) throws PsseModelException;
	public abstract float getToShuntG(int ndx) throws PsseModelException;
	public abstract float getToShuntB(int ndx) throws PsseModelException;
	public abstract Complex getToShuntY(int ndx) throws PsseModelException;

	
	/* defaults for convenience methods */
	
	public Bus getDeftFromBus(int ndx) throws PsseModelException {return _model.getBus(getI(ndx));}
	public Bus getDeftToBus(int ndx)  throws PsseModelException
	{
		String j = getJ(ndx);
		return _model.getBus((!j.isEmpty()&&j.charAt(0)=='-')?
			j.substring(1):j); 
	}
	public LineMeterEnd getDeftMeteredEnd(int ndx) throws PsseModelException
	{
		String j = getJ(ndx);
		return (!j.isEmpty() && j.charAt(0) == '-') ? 
			LineMeterEnd.To : LineMeterEnd.From;
	}
	public boolean getDeftInSvc(int ndx) throws PsseModelException {return getST(ndx) == 1;}
	public float getDeftR100(int ndx) throws PsseModelException {return PAMath.rebaseZ100(getR(ndx), _model.getSBASE());}
	public float getDeftX100(int ndx) throws PsseModelException {return PAMath.rebaseZ100(getX(ndx), _model.getSBASE());}
	public Complex getDeftZ100(int ndx) throws PsseModelException {return new Complex(getR100(ndx), getX100(ndx));}
	public float getDeftFromShuntG(int ndx) throws PsseModelException {return PAMath.rebaseZ100(getGI(ndx), _model.getSBASE());}
	public float getDeftFromShuntB(int ndx) throws PsseModelException {return PAMath.rebaseZ100(getBI(ndx), _model.getSBASE());}
	public Complex getDeftFromShuntY(int ndx) throws PsseModelException {return new Complex(getFromShuntG(ndx), getFromShuntB(ndx));}
	public float getDeftToShuntG(int ndx) throws PsseModelException {return PAMath.rebaseZ100(getGJ(ndx), _model.getSBASE());}
	public float getDeftToShuntB(int ndx) throws PsseModelException {return PAMath.rebaseZ100(getBJ(ndx), _model.getSBASE());}
	public Complex getDeftToShuntY(int ndx) throws PsseModelException {return new Complex(getToShuntG(ndx), getToShuntB(ndx));}

	/* raw PSS/e methods */
	public abstract String getI(int ndx) throws PsseModelException;
	public abstract String getJ(int ndx) throws PsseModelException;
	public abstract String getCKT(int ndx) throws PsseModelException;
	public abstract float getR(int ndx) throws PsseModelException;
	public abstract float getX(int ndx) throws PsseModelException;
	public abstract float getB(int ndx) throws PsseModelException;
	public abstract float getRATEA(int ndx) throws PsseModelException;
	public abstract float getRATEB(int ndx) throws PsseModelException;
	public abstract float getRATEC(int ndx) throws PsseModelException;
	public abstract float getGI(int ndx) throws PsseModelException;
	public abstract float getBI(int ndx) throws PsseModelException;
	public abstract float getGJ(int ndx) throws PsseModelException;
	public abstract float getBJ(int ndx) throws PsseModelException;
	public abstract int getST(int ndx) throws PsseModelException;
	public abstract float getLEN(int ndx) throws PsseModelException;

	public abstract OwnershipList<?> getOwnership(int ndx) throws PsseModelException;
	
	/* Defaults */
	public String getDeftCKT(int ndx) {return "1";}
	public float getDeftB(int ndx) {return 0F;}
	public float getDeftRATEA(int ndx) {return 0F;}
	public float getDeftRATEB(int ndx) {return 0F;}
	public float getDeftRATEC(int ndx) {return 0F;}
	public float getDeftGI(int ndx) {return 0F;}
	public float getDeftBI(int ndx) {return 0F;}
	public float getDeftGJ(int ndx) {return 0F;}
	public float getDeftBJ(int ndx) {return 0F;}
	public int getDeftST(int ndx) {return 1;}
	public float getDeftLEN(int ndx) {return 0F;}
	
	public OwnershipList<Ownership> getDeftOwnership(int ndx) {return null;}
}	
