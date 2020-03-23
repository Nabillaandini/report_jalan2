package ca.iam.rules;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ca.iam.backing.LoginBacking;
import ca.iam.backing.UserBacking;
import ca.iam.eao.SellEao;
import ca.iam.eao.UserEao;

@Stateless
public class UserRules implements Serializable{
	private static final long serialVersionUID = 6385474568024716609L;
	
	@EJB
	private UserEao userEao;

	@EJB
	private UserBacking userBacking;
}
