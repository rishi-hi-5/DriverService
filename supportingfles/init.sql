CREATE TABLE drivers (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(30) UNIQUE,
    vehicleId VARCHAR(50),
	licenseNumber VARCHAR(20),
    status VARCHAR(20) CHECK (status IN ('AVAILABLE', 'ON_TRIP','SUSPENDED')) DEFAULT 'SUSPENDED',
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);