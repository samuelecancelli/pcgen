/*
 * Copyright 2007, 2008 (C) Tom Parker <thpr@users.sourceforge.net>
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pcgen.cdom.base;

import java.util.List;

import pcgen.base.formula.Formula;
import pcgen.cdom.enumeration.AssociationListKey;
import pcgen.core.PlayerCharacter;

/**
 * This is a transitional class from PCGen 5.15+ to the final CDOM core. It is
 * provided as convenience to hold a set of choices and the number of choices
 * allowed, prior to final implementation of the new choice system
 * 
 * @param <T>
 */
public class PersistentTransitionChoice<T> extends TransitionChoice<T>
{

	private PersistentChoiceActor<T> choiceActor;

	public PersistentTransitionChoice(ChoiceSet<? extends T> cs, Formula count)
	{
		super(cs, count);
	}

	@Override
	public void setChoiceActor(ChoiceActor<T> ca)
	{
		choiceActor = (PersistentChoiceActor<T>) ca;
		super.setChoiceActor(ca);
	}

	public String encodeChoice(Object choice)
	{
		return choiceActor.encodeChoice(choice);
	}

	public T decodeChoice(String persistenceFormat)
	{
		return choiceActor.decodeChoice(persistenceFormat);
	}

	public void restoreChoice(PlayerCharacter pc, CDOMObject owner, T choice)
	{
		choiceActor.restoreChoice(pc, owner, choice);
	}

	public void remove(CDOMObject owner, PlayerCharacter apc)
	{
		List<Object> ch = apc.removeAllAssocs(this, AssociationListKey.ADD);
		if (ch != null)
		{
			for (Object o : ch)
			{
				choiceActor.removeChoice(apc, owner, castChoice(o));
			}
		}
	}

}
