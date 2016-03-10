/*CREATE TABLE MitchellClaims(
  ClaimNumber TEXT NOT NULL UNIQUE,
  ClaimInfo   XML 
);*/

--DROP TABLE mitchellclaims;
SELECT * from mitchellclaims;

SELECT claiminfo FROM mitchellclaims WHERE claimnumber='22c9c23bac142856018ce14a26b6c299';