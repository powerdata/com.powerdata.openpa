package com.powerdata.openpa.psse.csv;

import java.util.HashMap;

import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.ComplexList;
import com.powerdata.openpa.tools.LinkNet;

public class BusListElim extends BusList
{

	public BusListElim() {super();}

	public BusListElim(BusListRaw rbuses, LinkNet lnet, PsseModel model)
			throws PsseModelException
	{
		super(model);
		int[][] groups = lnet.findGroups();

		HashMap<String, String> elim = new HashMap<>();
		int nelim = 0;
		for (int[] grp : groups)
		{
			int ngrp = grp.length;
			String targ = rbuses.get(grp[0]).getObjectID();
			for (int i = 1; i < ngrp; ++i)
			{
				elim.put(rbuses.get(grp[i]).getObjectID(), targ);
				++nelim;
			}
		}
		System.out.format("Eliminating %d buses\n", nelim);
		int nrbus = rbuses.size();
		
		_size = nrbus - nelim;
		_i = new int[_size];
		_name = new String[_size];
		_ide = new int[_size];
		_area = new int[_size];
		_zone = new int[_size];
		_owner = new int[_size];
		_vm = new float[_size];
		_va = new float[_size];
		_gl = new float[_size];
		_bl = new float[_size];
		_ids = new String[_size];
		_basekv = new float[_size];
		_mm = new ComplexList(_size, true);
		
		HashMap<String,Integer> id2ndx = new HashMap<>(nrbus);
		int nbus = 0;
		for(int i=0; i < nrbus; ++i)
		{
			String objid = rbuses.getObjectID(i);
			String elimto = elim.get(objid);
			if (elimto == null)
			{
				_i[nbus] = rbuses.getI(i);
				_name[nbus] = rbuses.getNAME(i);
				_ids[nbus] = rbuses.getObjectID(i);
				_ide[nbus] = rbuses.getIDE(i);
				_area[nbus] = rbuses.getAREA(i);
				_zone[nbus] = rbuses.getZONE(i);
				_owner[nbus] = rbuses.getOWNER(i);
				_vm[nbus] = rbuses.getVM(i);
				_va[nbus] = rbuses.getVA(i);
				_gl[nbus] = rbuses.getGL(i);
				_bl[nbus] = rbuses.getBL(i);
				_basekv[nbus] = rbuses.getBASKV(i);
				id2ndx.put(objid, nbus);
				++nbus;
			}
			else
			{
				id2ndx.put(objid, id2ndx.get(elimto));
			}
		}
		_idToNdx = id2ndx;
	}


}
