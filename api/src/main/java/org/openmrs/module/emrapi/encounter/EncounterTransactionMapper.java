/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.emrapi.encounter;

import org.openmrs.Encounter;
import org.openmrs.module.emrapi.encounter.domain.EncounterTransaction;

public class EncounterTransactionMapper {
    private final EncounterObservationsMapper encounterObservationsMapper;
    private final EncounterOrdersMapper encounterOrdersMapper;

    public EncounterTransactionMapper(EncounterObservationsMapper encounterObservationsMapper, EncounterOrdersMapper encounterOrdersMapper) {
        this.encounterObservationsMapper = encounterObservationsMapper;
        this.encounterOrdersMapper = encounterOrdersMapper;
    }

    public EncounterTransaction map(Encounter encounter) {
        EncounterTransaction encounterTransaction = new EncounterTransaction(encounter.getVisit().getUuid(), encounter.getUuid());
        encounterTransaction.setPatientUuid(encounter.getPatient().getUuid());
        encounterTransaction.setEncounterTypeUuid(encounter.getEncounterType().getUuid());
        encounterTransaction.setLocationUuid(encounter.getLocation() != null ? encounter.getLocation().getUuid() : null);
        encounterTransaction.setVisitTypeUuid(encounter.getVisit().getVisitType().getUuid());
        encounterObservationsMapper.update(encounterTransaction, encounter.getObsAtTopLevel(true));
        encounterOrdersMapper.update(encounterTransaction, encounter.getOrders());
        return encounterTransaction;
    }
}