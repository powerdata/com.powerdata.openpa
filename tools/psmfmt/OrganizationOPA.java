package com.powerdata.openpa.tools.psmfmt;

import com.powerdata.openpa.OwnerList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;

public class OrganizationOPA extends ExportOpenPA<OwnerList>
{
	public OrganizationOPA(PAModel m) throws PAModelException
	{
		super(m.getOwners(), Organization.values().length);
		assign(Organization.ID, new StringWrap(i -> _list.getID(i)));
		assign(Organization.Name, new StringWrap(i -> _list.getName(i)));
	}
	@Override
	protected String getPsmFmtName()
	{
		return PsmMdlFmtObject.Organization.toString();
	}
}
