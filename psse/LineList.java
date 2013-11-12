package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.PAMath;

public abstract class LineList extends PsseBaseList<Line>
{
	public static final LineList Empty = new LineList()
	{
		@Override
		public String getI(int ndx) throws PsseModelException {return null;}
		@Override
		public String getJ(int ndx) throws PsseModelException {return null;}
		@Override
		public float getX(int ndx) throws PsseModelException {return 0f;}
		@Override
		public String getObjectID(int ndx) throws PsseModelException {return null;}
		@Override
		public int size() {return 0;}
	};
	
	protected LineList() {super();}
	public LineList(PsseModel model) {super(model);}

	/* Standard object retrieval */

	/** Get a NontransformerBranch by it's index. */
	@Override
	public Line get(int ndx) { return new Line(ndx,this); }
	/** Get a NontransformerBranch by it's ID. */
	@Override
	public Line get(String id) { return super.get(id); }

	
	/** From-side bus */
	public Bus getFromBus(int ndx) throws PsseModelException {return _model.getBus(getI(ndx));}
	/** To-side bus */
	public Bus getToBus(int ndx)  throws PsseModelException {return _model.getBus(getJ(ndx));}
	/** Get "metered" end */
	public LineMeterEnd getMeteredEnd(int ndx) throws PsseModelException {return LineMeterEnd.Unknown;}
	/** get initial branch status (ST) as a boolean.  Returns true if in service */
	public boolean isInSvc(int ndx) throws PsseModelException {return getST(ndx) == 1;}
	/** get complex impedance */
	public Complex getZ(int ndx) throws PsseModelException
	{
		return PAMath.rebaseZ100(new Complex(getR(ndx), getX(ndx)),
				_model.getSBASE());
	}
	public Complex getY(int ndx) throws PsseModelException {return getZ(ndx).inv();}
	public float getFromBchg(int ndx) throws PsseModelException {return getB(ndx)/2f;}
	public float getToBchg(int ndx) throws PsseModelException {return getB(ndx)/2f;}
	@Override
	public String getObjectName(int ndx) throws PsseModelException
	{
		return getFromBus(ndx).getObjectName()+"-"+getToBus(ndx).getObjectName()+":"+getCKT(ndx);
	}

	/* raw PSS/e methods */
	/** From-side bus number or name */
	public abstract String getI(int ndx) throws PsseModelException;
	/** To-side bus number or name */
	public abstract String getJ(int ndx) throws PsseModelException;
	/** circuit identifier */
	public String getCKT(int ndx) throws PsseModelException {return "1";}
	/** Branch resistance entered in p.u. */
	public float getR(int ndx) throws PsseModelException {return 0f;}
	/** Branch reactance entered in p.u. */
	public abstract float getX(int ndx) throws PsseModelException;
	/** Branch charging susceptance entered in p.u. */
	public float getB(int ndx) throws PsseModelException {return 0f;}
	/** First loading rating entered in MVA */
	public float getRATEA(int ndx) throws PsseModelException {return 0f;}
	/** Second loading rating entered in MVA */
	public float getRATEB(int ndx) throws PsseModelException {return 0f;}
	/** Third loading rating entered in MVA */
	public float getRATEC(int ndx) throws PsseModelException {return 0f;}
	/** conductance of line shunt at bus "I" */ 
	public float getGI(int ndx) throws PsseModelException {return 0f;}
	/** susceptance of line shunt at bus "I" */ 
	public float getBI(int ndx) throws PsseModelException {return 0f;}
	/** conductance of line shunt at bus "J" */ 
	public float getGJ(int ndx) throws PsseModelException {return 0f;}
	/** susceptance of line shunt at bus "J" */ 
	public float getBJ(int ndx) throws PsseModelException {return 0f;}
	/** Initial branch status (1 is in-service, 0 means out of service) */
	public int getST(int ndx) throws PsseModelException {return 1;}
	/** Line length  entered in user-selected units */
	public float getLEN(int ndx) throws PsseModelException {return 0f;}
	/** return Ownership as a list */
	public OwnershipList getOwnership(int ndx) throws PsseModelException {return OwnershipList.Empty;} //TODO: implement

	/* realtime fields */

	public float getMVA(int ndx) throws PsseModelException { return 0.0f; }
	public float getMVAPercent(int ndx) throws PsseModelException { return 0.0f; }
}	
