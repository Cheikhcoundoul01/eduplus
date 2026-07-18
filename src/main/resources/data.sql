-- ====================================================================
-- 1. ENSEIGNANTS
-- ====================================================================
INSERT INTO enseignants (nom, prenom, email, specialite)
SELECT 'Diop', 'Awa', 'awa.diop@eduplus.sn', 'Mathematiques'
    WHERE NOT EXISTS (SELECT 1 FROM enseignants WHERE email = 'awa.diop@eduplus.sn');

INSERT INTO enseignants (nom, prenom, email, specialite)
SELECT 'Fall', 'Moussa', 'moussa.fall@eduplus.sn', 'Informatique'
    WHERE NOT EXISTS (SELECT 1 FROM enseignants WHERE email = 'moussa.fall@eduplus.sn');

INSERT INTO enseignants (nom, prenom, email, specialite)
SELECT 'Sarr', 'Fatou', 'fatou.sarr@eduplus.sn', 'Physique'
    WHERE NOT EXISTS (SELECT 1 FROM enseignants WHERE email = 'fatou.sarr@eduplus.sn');

-- ====================================================================
-- 2. COURS
-- ====================================================================
INSERT INTO cours (code, nom, credits, enseignant_id)
SELECT 'INF301', 'Developpement Web Avance', 6, (SELECT id FROM enseignants WHERE email = 'moussa.fall@eduplus.sn')
    WHERE NOT EXISTS (SELECT 1 FROM cours WHERE code = 'INF301');

INSERT INTO cours (code, nom, credits, enseignant_id)
SELECT 'MAT201', 'Algebre Lineaire', 4, (SELECT id FROM enseignants WHERE email = 'awa.diop@eduplus.sn')
    WHERE NOT EXISTS (SELECT 1 FROM cours WHERE code = 'MAT201');

INSERT INTO cours (code, nom, credits, enseignant_id)
SELECT 'PHY101', 'Physique Generale', 4, (SELECT id FROM enseignants WHERE email = 'fatou.sarr@eduplus.sn')
    WHERE NOT EXISTS (SELECT 1 FROM cours WHERE code = 'PHY101');

-- ====================================================================
-- 3. ETUDIANTS
-- ====================================================================
INSERT INTO etudiants (matricule, nom, prenom, email, date_naissance)
SELECT 'ETU2026001', 'Ndiaye', 'Cheikh', 'cheikh.ndiaye@eduplus.sn', '2003-04-12'
    WHERE NOT EXISTS (SELECT 1 FROM etudiants WHERE matricule = 'ETU2026001');

INSERT INTO etudiants (matricule, nom, prenom, email, date_naissance)
SELECT 'ETU2026002', 'Ba', 'Aminata', 'aminata.ba@eduplus.sn', '2002-11-03'
    WHERE NOT EXISTS (SELECT 1 FROM etudiants WHERE matricule = 'ETU2026002');

INSERT INTO etudiants (matricule, nom, prenom, email, date_naissance)
SELECT 'ETU2026003', 'Diallo', 'Ibrahima', 'ibrahima.diallo@eduplus.sn', '2003-01-22'
    WHERE NOT EXISTS (SELECT 1 FROM etudiants WHERE matricule = 'ETU2026003');

INSERT INTO etudiants (matricule, nom, prenom, email, date_naissance)
SELECT 'ETU2026004', 'Sy', 'Mariama', 'mariama.sy@eduplus.sn', '2003-07-15'
    WHERE NOT EXISTS (SELECT 1 FROM etudiants WHERE matricule = 'ETU2026004');

INSERT INTO etudiants (matricule, nom, prenom, email, date_naissance)
SELECT 'ETU2026005', 'Gueye', 'Ousmane', 'ousmane.gueye@eduplus.sn', '2002-09-30'
    WHERE NOT EXISTS (SELECT 1 FROM etudiants WHERE matricule = 'ETU2026005');

--- ====================================================================
-- 4. COMPTES USERS POUR LA SÉCURITÉ (Mot de passe : Admin@123)
-- ====================================================================
-- Compte admin de demo
INSERT INTO users (username, email, password, role, enabled)
SELECT 'admin', 'admin@eduplus.sn', '$2b$10$NGD3zxWacDjKGUzuaPw2qOkg92eLbQFm.3uMUKOfzWikt90w56PHO', 'ADMIN', true
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');

-- Compte de demo pour un Enseignant (Prof. Fall)
INSERT INTO users (username, email, password, role, enabled)
SELECT 'moussa.fall', 'moussa.fall@eduplus.sn', '$2b$10$NGD3zxWacDjKGUzuaPw2qOkg92eLbQFm.3uMUKOfzWikt90w56PHO', 'ENSEIGNANT', true
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'moussa.fall');

-- Compte de demo pour un Étudiant (Cheikh)
INSERT INTO users (username, email, password, role, enabled)
SELECT 'cheikh.ndiaye', 'cheikh.ndiaye@eduplus.sn', '$2b$10$NGD3zxWacDjKGUzuaPw2qOkg92eLbQFm.3uMUKOfzWikt90w56PHO', 'ETUDIANT', true
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'cheikh.ndiaye');