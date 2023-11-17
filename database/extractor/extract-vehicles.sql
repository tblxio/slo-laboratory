  SELECT f.operator_id, f.fleet_id, v.vehicle_id
    FROM fms.fleets f
    JOIN fms.vehicles v on v.fleet_id = f.fleet_id
ORDER BY f.operator_id, f.fleet_id, v.vehicle_id