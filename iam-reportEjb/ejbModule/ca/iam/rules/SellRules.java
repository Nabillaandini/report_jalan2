package ca.iam.rules;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ca.iam.backing.LoginBacking;
import ca.iam.eao.SellEao;

@Stateless
public class SellRules implements Serializable{
	private static final long serialVersionUID = 6385474568024716609L;
	
	@EJB
	private SellEao sellEao;

	@EJB
	private LoginBacking loginBacking;
}
