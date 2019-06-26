package com.love.family.system.dao;

import java.io.Serializable;

public interface Entity<ID extends Serializable> extends Serializable {
	ID getId();

	boolean isDeletable();
}
