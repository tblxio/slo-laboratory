import papaparse from 'https://jslib.k6.io/papaparse/5.1.1/index.js';
import { randomItem } from 'https://jslib.k6.io/k6-utils/1.2.0/index.js';
import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
    stages: [
        { duration: '30s', target: 50 }, // Ramp up to 20 users over 30 seconds
        { duration: '1d', target: 50 },  // Stay at 20 users for 1 hour
        { duration: '30s', target: 0 },  // Ramp down to 0 users over 30 seconds
    ],
};

const operators = function() {
    const rawDrivers = papaparse.parse(open('./data/drivers.csv'), { header: true, skipEmptyLines: true }).data;
    const rawVehicles = papaparse.parse(open('./data/vehicles.csv'), { header: true, skipEmptyLines: true }).data;

    // Group vehicles by operator and fleet
    const vehicleMap = {};
    rawVehicles.forEach(({ operator_id, fleet_id, vehicle_id }) => {
        if (!vehicleMap[operator_id]) vehicleMap[operator_id] = {};
        if (!vehicleMap[operator_id][fleet_id]) vehicleMap[operator_id][fleet_id] = [];
        vehicleMap[operator_id][fleet_id].push(vehicle_id);
    });

    // Group drivers by operator
    const driverMap = {};
    rawDrivers.forEach(({ operator_id, driver_id }) => {
        if (!driverMap[operator_id]) driverMap[operator_id] = [];
        driverMap[operator_id].push(driver_id);
    });

    const result = Object.keys(vehicleMap).map(operatorId => ({
        operatorId: operatorId,
        drivers: driverMap[operatorId] || [],
        fleets: Object.keys(vehicleMap[operatorId]).map(fleetId => ({
            fleetId: fleetId,
            vehicles: vehicleMap[operatorId][fleetId]
        }))
    }));

    return result
}();

export default function () {
    let baseUrl = 'http://rest-api:8080';

    // GET /operators
    check(http.get(`${baseUrl}/operators`), {
        'GET /operators status is 200': (r) => r.status === 200,
    });

    // You need to replace these IDs with actual IDs from your database or another source
    let operator = randomItem(operators);

    // GET /operators/{operatorId}
    check(http.get(`${baseUrl}/operators/${operator.operatorId}`), {
        'GET /operators/{operatorId} status is 200': (r) => r.status === 200,
    });

    if (operator.fleets.some((element) => true)) {
        // GET /operators/{operatorId}/fleets
        check(http.get(`${baseUrl}/operators/${operator.operatorId}/fleets`), {
            'GET /operators/{operatorId}/fleets status is 200': (r) => r.status === 200,
        });
    
        let fleet = randomItem(operator.fleets);
        // GET /operators/{operatorId}/fleets/{fleetId}
        check(http.get(`${baseUrl}/operators/${operator.operatorId}/fleets/${fleet.fleetId}`), {
            'GET /operators/{operatorId}/fleets/{fleetId} status is 200': (r) => r.status === 200,
        });

        if (fleet.vehicles.some((element) => true)) {
            // GET /operators/{operatorId}/fleets/{fleetId}/vehicles
            check(http.get(`${baseUrl}/operators/${operator.operatorId}/fleets/${fleet.fleetId}/vehicles`), {
                'GET /operators/{operatorId}/fleets/{fleetId}/vehicles status is 200': (r) => r.status === 200,
            });

            let vehicleId = randomItem(fleet.vehicles);
            // GET /operators/{operatorId}/fleets/{fleetId}/vehicles/{vehicleId}
            check(http.get(`${baseUrl}/operators/${operator.operatorId}/fleets/${fleet.fleetId}/vehicles/${vehicleId}`), {
                'GET /operators/{operatorId}/fleets/{fleetId}/vehicles/{vehicleId} status is 200': (r) => r.status === 200,
            });
        } else {
            // GET /operators/{operatorId}/fleets/{fleetId}/vehicles
            check(http.get(`${baseUrl}/operators/${operator.operatorId}/fleets/${fleet.fleetId}/vehicles`), {
                'GET /operators/{operatorId}/fleets/{fleetId}/vehicles status is 404': (r) => r.status === 404,
            });
        }
    } else {
        // GET /operators/{operatorId}/fleets
        check(http.get(`${baseUrl}/operators/${operator.operatorId}/fleets`), {
            'GET /operators/{operatorId}/fleets status is 404': (r) => r.status === 404,
        });
    }


    if (operator.drivers.some((element) => true)) {
        // GET /operators/{operatorId}/drivers
        check(http.get(`${baseUrl}/operators/${operator.operatorId}/drivers`), {
            'GET /operators/{operatorId}/drivers status is 200': (r) => r.status === 200,
        });

        let driverId = randomItem(operator.drivers);
        // GET /operators/{operatorId}/drivers/{driverId}
        check(http.get(`${baseUrl}/operators/${operator.operatorId}/drivers/${driverId}`), {
            'GET /operators/{operatorId}/drivers/{driverId} status is 200': (r) => r.status === 200,
        });
    } else {
        // GET /operators/{operatorId}/drivers
        check(http.get(`${baseUrl}/operators/${operator.operatorId}/drivers`), {
            'GET /operators/{operatorId}/drivers status is 404': (r) => r.status === 404,
        });
    }

    // Additional endpoints can be added here in a similar manner

    sleep(1); // Wait 1 second between iterations
}