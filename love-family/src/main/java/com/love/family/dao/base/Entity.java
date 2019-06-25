package com.love.family.dao.base;

import java.io.Serializable;

public interface Entity<ID extends Serializable> extends Serializable {
	ID getId();

	boolean isDeletable();
}
