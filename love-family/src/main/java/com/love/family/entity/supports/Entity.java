package com.love.family.entity.supports;

import java.io.Serializable;

public interface Entity<ID extends Serializable> extends Serializable {
	ID getId();

	boolean isDeletable();
}
