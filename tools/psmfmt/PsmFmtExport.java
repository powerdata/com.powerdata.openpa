package com.powerdata.openpa.tools.psmfmt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PflowModelBuilder;
import com.powerdata.openpa.pwrflow.BusRefIndex;

public class PsmFmtExport
{
	PAModel _model;
	BusRefIndex _bri;
	boolean _sbus;
	String _mdlname, _mdldesc, _mdlver;
	Float _freq;

	public PsmFmtExport(PAModel model, boolean singlebus) throws PAModelException
	{
		_model = model;
		_bri = singlebus ? BusRefIndex.CreateFromSingleBus(model) : 
			BusRefIndex.CreateFromConnectivityBus(model);
		_sbus = singlebus;
	}
	
	public void export(File odir) throws PAModelException, IOException
	{
		if (!odir.exists()) odir.mkdirs();
		exportMeta(odir);
		new ControlAreaOPA(_model).export(odir);
		new GeneratingUnitOPA(_model).export(odir);
		new LineOPA(_model, _bri).export(odir);
		new LoadOPA(_model, _bri).export(odir);
		new NodeOPA(_bri).export(odir);
		new OrganizationOPA(_model).export(odir);
		new PhaseTapChangerOPA(_model, _bri).export(odir);
		new RatioTapChangerOPA(_model, _bri).export(odir);
		new SeriesCapacitorOPA(_model, _bri).export(odir);
		new SeriesReactorOPA(_model, _bri).export(odir);
		new ShuntCapacitorOPA(_model, _bri).export(odir);
		new ShuntReactorOPA(_model, _bri).export(odir);
		new SubstationOPA(_model).export(odir);
		new SynchronousMachineOPA(_model, _bri).export(odir);
		new SvcOPA(_model, _bri).export(odir);
		if (_sbus)
		{
			File f = new File(odir, PsmMdlFmtObject.Switch.toString()+".csv");
			if (f.exists()) f.delete();
		}
		else
			new SwitchOPA(_model, _bri).export(odir);
		new CaseLoadOPA(_model).export(odir);
		new CaseGeneratingUnitOPA(_model).export(odir);
		new CaseSynchronousMachineOPA(_model).export(odir);
		new CaseSvcOPA(_model).export(odir);
		new CasePhaseTapChangerOPA(_model).export(odir);
		new CaseRatioTapChangerOPA(_model).export(odir);
		new TransformerOPA(_model).export(odir);
		new TransformerWindingOPA(_model, _bri).export(odir);
	}
	
	void exportMeta(File odir) throws IOException
	{
		String fn = PsmMdlFmtObject.ModelParameters.toString() + ".csv";
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(odir, fn))));
		expMetaParm(pw, ModelParameter.ParameterName.toString(), 
			ModelParameter.ParameterValue.toString());
		expMetaParm(pw, "ModelFormatVersion", PsmMdlFmtObject.GetVersion());
		expMetaParm(pw, "ModelName", getModelName());
		expMetaParm(pw, "ModelDescription", getModelDescription());
		expMetaParm(pw, "ModelDataVersion", getModelVersion());
		expMetaParm(pw, "SimulationFrequencySetpoint", getFrequency());
		pw.close();
	}

	void expMetaParm(PrintWriter pw, String key, Object value)
	{
		if (value != null)
			pw.format("\"%s\",\"%s\"\n", key, value.toString());
	}

	public void setModelName(String name) {_mdlname = name;}
	public String getModelName() {return _mdlname;}
	public void setModelDescription(String desc) {_mdldesc = desc;}
	public String getModelDescription() {return _mdldesc;}
	public void setModelVersion(String version) {_mdlver = version;}
	public String getModelVersion() {return _mdlver;}
	public void setFrequency(float freq) {_freq = freq;}
	public Float getFrequency() {return _freq;}
	
	public static void main(String...args) throws Exception
	{
		String uri = null;
		File outdir = new File(System.getProperty("user.dir"));
		boolean useSingleBus = false;
		String mdlname = null;
		for(int i=0; i < args.length;)
		{
			String s = args[i++].toLowerCase();
			int ssx = 1;
			if (s.startsWith("--")) ++ssx;
			switch(s.substring(ssx))
			{
				case "uri":
					uri = args[i++];
					break;
				case "outdir":
					outdir = new File(args[i++]);
					break;
				case "singlebus":
					useSingleBus = true;
					break;
				case "modelname":
					mdlname = args[i++];
					break;
			}
		}
		if (uri == null)
		{
			System.err.format("Usage: -uri model_uri "
					+ "[ --outdir output_directory (deft to $CWD ]\n");
			System.exit(1);
		}
		PflowModelBuilder bldr = PflowModelBuilder.Create(uri);
		bldr.enableFlatVoltage(true);
		PsmFmtExport exp = new PsmFmtExport(bldr.load(), useSingleBus);
		exp.setModelName(mdlname);
		exp.export(outdir);
	}

}
