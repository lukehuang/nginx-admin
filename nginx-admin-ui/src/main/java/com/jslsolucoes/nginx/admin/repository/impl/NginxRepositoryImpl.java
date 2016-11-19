/*******************************************************************************
 * Copyright 2016 JSL Solucoes LTDA - https://jslsolucoes.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.jslsolucoes.nginx.admin.repository.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jslsolucoes.nginx.admin.i18n.Messages;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;

@RequestScoped
public class NginxRepositoryImpl extends RepositoryImpl<Nginx> implements NginxRepository {

	public NginxRepositoryImpl() {

	}

	@Inject
	public NginxRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Nginx nginx() {
		Query query = entityManager.createQuery("from Nginx");
		return (Nginx) query.getSingleResult();
	}

	@Override
	public List<String> validateBeforeUpdate(Nginx nginx) {
		List<String> errors = new ArrayList<String>();

		if (!new File(nginx.getHome()).exists()) {
			errors.add(Messages.getString("nginx.invalid.home.folder", nginx.getHome()));
		}

		if (!new File(nginx.getBin()).exists()) {
			errors.add(Messages.getString("nginx.invalid.bin.file", nginx.getBin()));
		}

		if (!new File(nginx.getConf()).exists()) {
			errors.add(Messages.getString("nginx.invalid.conf.file", nginx.getConf()));
		}

		if (!new File(nginx.getError()).exists()) {
			errors.add(Messages.getString("nginx.invalid.error.file", nginx.getError()));
		}

		if (!new File(nginx.getAccess()).exists()) {
			errors.add(Messages.getString("nginx.invalid.access.file", nginx.getAccess()));
		}

		return errors;
	}
}
